package com.gigalike.order.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCartItemQuantity {
    UUID cartItemId;
    UUID cartId;
    Integer quantity;
}
