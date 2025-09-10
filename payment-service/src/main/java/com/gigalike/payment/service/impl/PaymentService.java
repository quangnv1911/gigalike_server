package com.gigalike.payment.service.impl;

import com.gigalike.payment.constant.PaymentStatus;
import com.gigalike.payment.constant.PaymentType;
import com.gigalike.payment.dto.data.PaymentConfigDto;
import com.gigalike.payment.dto.request.GenerateNewPaymentRequest;
import com.gigalike.payment.dto.response.GenerateNewPaymentResponse;
import com.gigalike.payment.entity.Payment;
import com.gigalike.payment.repository.PaymentRepository;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.payment.service.IPaymentService;
import com.gigalike.shared.dto.VietQrConfigDto;
import com.gigalike.shared.util.PaymentUtil;
import com.gigalike.shared.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService implements IPaymentService {
    PaymentRepository paymentRepository;
    IPaymentConfigService paymentConfigService;

    @Override
    @Transactional
    public GenerateNewPaymentResponse generateNewPayment(GenerateNewPaymentRequest request) {
        String userName = SecurityUtil.getCurrentUsername();
        PaymentConfigDto paymentConfigDto = paymentConfigService.getPaymentConfigById(request.getPaymentConfigId());
        var payment = createNewPayment(request.getAmount(), paymentConfigDto.getPaymentType());
        log.info("Payment created: {}", payment);
        String description = PaymentUtil.generatePaymentString(userName);
        VietQrConfigDto vietQrConfigDto = VietQrConfigDto.builder()
                .description(description)
                .accountName(paymentConfigDto.getUserName())
                .bankId(paymentConfigDto.getBankBin())
                .template("compact2")
                .accountNo(paymentConfigDto.getCardNumber())
                .build();
        String qrCodeUrl = PaymentUtil.generateQrUrl(vietQrConfigDto, request.getAmount());
        return GenerateNewPaymentResponse.builder()
                .amount(request.getAmount())
                .message(description)
                .cardNumber(paymentConfigDto.getCardNumber())
                .bankName(paymentConfigDto.getBankName())
                .qrCode(qrCodeUrl)
                .build();
    }

    private Payment createNewPayment(BigDecimal amount, PaymentType paymentType) {
        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.UNPAID)
                .paymentType(paymentType)
                .amount(amount)
                .build();
        return paymentRepository.save(payment);
    }
}
