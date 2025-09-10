package com.gigalike.marketing.service;

import com.gigalike.marketing.dto.request.CouponRequest;
import com.gigalike.marketing.dto.request.CouponSearchRequest;
import com.gigalike.marketing.dto.response.CouponResponse;
import com.gigalike.shared.dto.PageResponse;
import java.util.UUID;

public interface ICouponService {
    PageResponse<CouponResponse> getCoupons(CouponSearchRequest couponSearchRequest);
    CouponResponse getCouponById(UUID couponId);
    CouponResponse createCoupon(CouponRequest newCoupon);
    CouponResponse updateCoupon(UUID couponId, CouponRequest newCoupon);
    void deleteCouponById(UUID couponId);
}
