package com.gigalike.payment.service.impl;

import com.gigalike.payment.dto.data.PaymentDto;
import com.gigalike.payment.entity.PaymentConfig;
import com.gigalike.payment.repository.PaymentConfigRepository;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.shared.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConfigService implements IPaymentConfigService {
    PaymentConfigRepository paymentConfigRepository;


    @Override
    public void createPaymentConfig(PaymentDto paymentDto) {
        var paymentConfig = PaymentConfig.builder()
                .enabled(paymentDto.isEnable())
                .paymentType(paymentDto.getPaymentType())
                .cardNumber(paymentDto.getCardNumber())
                .bankName(paymentDto.getBankName())
                .userName(paymentDto.getUserName())
                .build();

        PaymentConfig newPayment = paymentConfigRepository.save(paymentConfig);
        log.info("Payment with id {} config has been added successfully", newPayment.getId());
    }

    @Override
    public void updatePaymentConfig(UUID paymentConfigId, PaymentDto paymentDto) {
        var paymentConfig = getPaymentConfig(paymentConfigId);
        paymentConfig.setEnabled(paymentDto.isEnable());
        paymentConfig.setPaymentType(paymentDto.getPaymentType());
        paymentConfig.setCardNumber(paymentDto.getCardNumber());
        paymentConfig.setBankName(paymentDto.getBankName());
        paymentConfig.setUserName(paymentDto.getUserName());
        paymentConfigRepository.save(paymentConfig);
        log.info("Payment with id {} config has been saved successfully", paymentConfigId);
    }

    @Override
    public void deletePaymentConfig(UUID paymentConfigId) {
        var paymentConfig = getPaymentConfig(paymentConfigId);
        paymentConfigRepository.delete(paymentConfig);
        log.info("Payment {} config has been deleted", paymentConfigId);
    }

    @Override
    public List<PaymentDto> getPaymentConfig() {
        return paymentConfigRepository.findAll()
                .stream()
                .map(config -> PaymentDto.builder()
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
