package com.gigalike.marketing.dto.response;

import com.gigalike.shared.constant.CouponType;
import com.gigalike.marketing.entity.Coupon;
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

    Integer maxUsed;

    LocalDateTime updatedAt;
    LocalDateTime expiredIn;

    public static CouponResponse fromCoupon(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .type(coupon.getType())
                .value(coupon.getValue())
                .totalUsed(coupon.getTotalUsed())
                .updatedAt(coupon.getUpdatedAt())
                .code(coupon.getCode())
                .maxUsed(coupon.getMaxUsed())
                .expiredIn(coupon.getExpiredIn())
                .build();
    }
}
