package com.gigalike.marketing.dto.request;

import com.gigalike.shared.constant.CouponType;
import com.gigalike.marketing.entity.Coupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponRequest {
    String name;

    @Builder.Default
    CouponType type =  CouponType.FIXED_AMOUNT;

    @Builder.Default
    BigDecimal value = BigDecimal.ZERO;

    @Builder.Default
    int totalUsed = 0;
    Integer maxUsed;

    LocalDateTime expiredIn;

    public static Coupon convertToCoupon(CouponRequest couponRequest) {
        return Coupon.builder()
                .name(couponRequest.getName())
                .type(couponRequest.getType())
                .value(couponRequest.getValue())
                .maxUsed(couponRequest.getMaxUsed())
                .totalUsed(couponRequest.getTotalUsed())
                .expiredIn(couponRequest.getExpiredIn())
                .build();
    }
}
