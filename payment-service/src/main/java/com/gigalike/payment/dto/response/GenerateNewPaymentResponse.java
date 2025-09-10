package com.gigalike.payment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GenerateNewPaymentResponse {
    String message;
    String cardNumber;
    String bankName;
    String qrCode;
    BigDecimal amount;
}
