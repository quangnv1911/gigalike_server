package com.gigalike.order.dto.data;

import com.gigalike.shared.constant.CouponType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse {
    UUID couponId;
    String name;

    String code;

    @Builder.Default
    CouponType type = CouponType.FIXED_AMOUNT;

    @Builder.Default
    BigDecimal value = BigDecimal.ZERO;

    @Builder.Default
    int totalUsed = 0;
    @Builder.Default
    int maxUsed = 0;

    LocalDateTime updatedAt;
    LocalDateTime expiredIn;
}
