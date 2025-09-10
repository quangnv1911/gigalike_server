package com.gigalike.payment.dto.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gigalike.payment.constant.PaymentType;
import com.gigalike.payment.entity.PaymentConfig;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE
)
public class PaymentConfigDto {
    @Nullable
    UUID id;
    String bankName;

    String bankBin;
    String cardNumber;
    String userName;
    boolean isEnable;
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    public static PaymentConfigDto fromPaymentConfig(PaymentConfig paymentConfig) {
        return PaymentConfigDto.builder()
                .id(paymentConfig.getId())
                .bankBin(paymentConfig.getBankBin())
                .bankName(paymentConfig.getBankName())
                .cardNumber(paymentConfig.getCardNumber())
                .userName(paymentConfig.getUserName())
                .isEnable(paymentConfig.isEnabled())
                .paymentType(paymentConfig.getPaymentType())
                .build();
    }
}
