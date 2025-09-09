package com.gigalike.payment.service;

import com.gigalike.payment.dto.data.CassoConfigDto;
import com.gigalike.payment.dto.data.PaymentConfigDto;
import com.gigalike.payment.entity.PaymentConfig;

import java.util.List;
import java.util.UUID;

public interface IPaymentConfigService {
    PaymentConfigDto createPaymentConfig(PaymentConfigDto paymentDto);
    PaymentConfigDto updatePaymentConfig(UUID paymentConfigId, PaymentConfigDto paymentDto);
    void deletePaymentConfig(UUID paymentConfigId);
    List<PaymentConfigDto> getAllPaymentConfig();
    PaymentConfigDto getPaymentConfigById(UUID paymentConfigId);
    CassoConfigDto parseCassoConfig(UUID paymentConfigId);
}
