package com.gigalike.order.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartRqDto {
    String accessToken;
    UUID productId;
    UUID productDurationId;
    Integer quantity;
}