package com.gigalike.payment.service;

import com.gigalike.payment.dto.data.PaymentDto;

import java.util.List;
import java.util.UUID;

public interface IPaymentConfigService {
    void createPaymentConfig(PaymentDto paymentDto);
    void updatePaymentConfig(UUID paymentConfigId, PaymentDto paymentDto);
    void deletePaymentConfig(UUID paymentConfigId);
    List<PaymentDto> getPaymentConfig();

}
