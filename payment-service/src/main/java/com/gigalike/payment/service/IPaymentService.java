package com.gigalike.payment.service;

import com.gigalike.payment.dto.request.GenerateNewPaymentRequest;
import com.gigalike.payment.dto.response.GenerateNewPaymentResponse;

public interface IPaymentService {
    GenerateNewPaymentResponse generateNewPayment(GenerateNewPaymentRequest request);
}
