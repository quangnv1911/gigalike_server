package com.gigalike.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigalike.payment.dto.data.BankInfoDto;
import com.gigalike.payment.dto.data.CassoConfigDto;
import com.gigalike.payment.dto.data.PaymentConfigDto;
import com.gigalike.payment.dto.response.BankInfoListResponse;
import com.gigalike.payment.entity.PaymentConfig;
import com.gigalike.payment.repository.PaymentConfigRepository;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.shared.exception.NotFoundException;
import com.gigalike.shared.util.HttpUtil;
import com.gigalike.shared.util.PaymentUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConfigService implements IPaymentConfigService {
    PaymentConfigRepository paymentConfigRepository;
    ObjectMapper objectMapper;
    HttpUtil httpUtil;

    @Override
    @Transactional
    public PaymentConfigDto createPaymentConfig(PaymentConfigDto paymentDto) {
        var paymentConfig = PaymentConfig.builder()
                .enabled(paymentDto.isEnable())
                .paymentType(paymentDto.getPaymentType())
                .cardNumber(paymentDto.getCardNumber())
                .bankName(paymentDto.getBankName())
                .userName(paymentDto.getUserName())
                .build();

        PaymentConfig newPayment = paymentConfigRepository.save(paymentConfig);
        log.info("Payment with id {} config has been added successfully", newPayment.getId());
        return PaymentConfigDto.fromPaymentConfig(newPayment);
    }

    @Override
    @Transactional
    public PaymentConfigDto updatePaymentConfig(UUID paymentConfigId, PaymentConfigDto paymentDto) {
        var paymentConfig = getPaymentConfig(paymentConfigId);
        paymentConfig.setEnabled(paymentDto.isEnable());
        paymentConfig.setPaymentType(paymentDto.getPaymentType());
        paymentConfig.setCardNumber(paymentDto.getCardNumber());
        paymentConfig.setBankName(paymentDto.getBankName());
        paymentConfig.setUserName(paymentDto.getUserName());
        var newPaymentConfig = paymentConfigRepository.save(paymentConfig);
        log.info("Payment with id {} config has been saved successfully", paymentConfigId);
        return PaymentConfigDto.fromPaymentConfig(newPaymentConfig);
    }

    @Override
    @Transactional
    public void deletePaymentConfig(UUID paymentConfigId) {
        var paymentConfig = getPaymentConfig(paymentConfigId);
        paymentConfigRepository.delete(paymentConfig);
        log.info("Payment {} config has been deleted", paymentConfigId);
    }

    @Override
    public PaymentConfigDto getPaymentConfigById(UUID paymentConfigId) {
        return PaymentConfigDto.fromPaymentConfig(getPaymentConfig(paymentConfigId));
    }

    @Override
    public CassoConfigDto parseCassoConfig(UUID paymentConfigId) {
        var paymentConfig = getPaymentConfig(paymentConfigId);
        try {
            return objectMapper.readValue(paymentConfig.getConfig(), CassoConfigDto.class);
        } catch (Exception e) {
            log.error("Could not parse CassoConfigDto from PaymentConfig with id {}", paymentConfig.getId());
            throw new IllegalArgumentException("Invalid config JSON for payment id " + paymentConfig.getId(), e);
        }
    }

    @Override
    public List<BankInfoDto> getAllBankInfo() {
        BankInfoListResponse response = httpUtil.callAPI(
                PaymentUtil.urlGetBankInfo,
                null,
                HttpMethod.GET,
                null,
                BankInfoListResponse.class
        );

        if (response != null && "00".equals(response.getCode())) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    @Override
    public List<PaymentConfigDto> getAllPaymentConfig() {
        return paymentConfigRepository.findAll()
                .stream()
                .map(config -> PaymentConfigDto.builder()
                        .id(config.getId())
                        .bankName(config.getBankName())
                        .cardNumber(config.getCardNumber())
                        .paymentType(config.getPaymentType())
                        .isEnable(config.isEnabled())
                        .userName(config.getUserName())
                        .build()
                )
                .toList();
    }


    private PaymentConfig getPaymentConfig(UUID paymentConfigId) {
        var paymentConfig = paymentConfigRepository.findById(paymentConfigId);
        if (paymentConfig.isEmpty()) {
            throw new NotFoundException("Payment config with id " + paymentConfigId + " not found");
        }
        return paymentConfig.get();
    }


}
