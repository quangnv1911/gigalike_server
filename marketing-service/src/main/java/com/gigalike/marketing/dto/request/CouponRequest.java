package com.gigalike.marketing.dto.request;

import com.gigalike.marketing.constant.CouponType;
import com.gigalike.marketing.entity.Coupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponRequest {
    String name;

    @Builder.Default
    CouponType type =  CouponType.FIXED_AMOUNT;

    @Builder.Default
    int value = 0;

    @Builder.Default
    int totalUsed = 0;
@Builder.Default
    int maxUsed = 0;

    public static Coupon convertToCoupon(CouponRequest couponRequest) {
        return Coupon.builder()
                .name(couponRequest.getName())
                .type(couponRequest.getType())
                .value(couponRequest.getValue())
                .maxUsed(couponRequest.getMaxUsed())
                .totalUsed(couponRequest.getTotalUsed())
                .build();
    }
}
