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
    String cardNumber;
    String userName;
    boolean isEnable;
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    public static PaymentConfigDto fromPaymentConfig(PaymentConfig user) {
        return PaymentConfigDto.builder()
                .id(user.getId())
                .bankName(user.getBankName())
                .cardNumber(user.getCardNumber())
                .userName(user.getUserName())
                .isEnable(user.isEnabled())
                .paymentType(user.getPaymentType())
                .build();
    }
}
