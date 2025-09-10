package com.gigalike.payment.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class GenerateNewPaymentRequest {
    BigDecimal amount;
    UUID paymentConfigId;
}
