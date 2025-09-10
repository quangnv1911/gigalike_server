package com.gigalike.marketing.dto.response;

import com.gigalike.marketing.constant.CouponType;
import com.gigalike.marketing.entity.Coupon;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
    int value = 0;

    @Builder.Default
    int totalUsed = 0;
    @Builder.Default
    int maxUsed = 0;

    LocalDateTime updatedAt;

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
                .build();
    }
}
