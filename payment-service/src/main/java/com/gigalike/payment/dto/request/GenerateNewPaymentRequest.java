package com.gigalike.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class GenerateNewPaymentRequest {
    @NotBlank(message = "Amount is required")
    @Size(min = 5000, message = "Amount must higher than 5000")
    BigDecimal amount;

    @NotBlank(message = "Payment config is required")
    UUID paymentConfigId;
}
