package com.gigalike.order.dto.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdateUserAmount {
    BigDecimal amount;
}
