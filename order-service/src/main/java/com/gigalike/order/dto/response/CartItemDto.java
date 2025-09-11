package com.gigalike.order.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {
    UUID cartItemId;
    UUID productId;
    String productName;
    int quantity;
    BigDecimal price;
    List<String> imageUrls;
}
