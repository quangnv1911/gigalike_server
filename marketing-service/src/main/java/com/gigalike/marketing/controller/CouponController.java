package com.gigalike.marketing.controller;

import com.gigalike.marketing.dto.request.CouponRequest;
import com.gigalike.marketing.dto.request.CouponSearchRequest;
import com.gigalike.marketing.service.ICouponService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponController {
    ICouponService couponService;

    @GetMapping("/") 
    public ResponseEntity<ApiResponse<?>> getAllCoupons(@ModelAttribute CouponSearchRequest request) {
        var response = couponService.getCoupons(request);
        return ResponseEntity.ok(ApiResponse.success("Get all coupons successfully", response));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<ApiResponse<?>> getCouponDetail(@PathVariable UUID couponId) {
        var response = couponService.getCouponById(couponId);
        return ResponseEntity.ok(ApiResponse.success("Get coupon detail successfully", response));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> createCoupon(@RequestBody CouponRequest coupon) {
        var response = couponService.createCoupon(coupon);
        return ResponseEntity.ok(ApiResponse.success("Create coupon successfully", response));
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<ApiResponse<?>> updateCoupon(@PathVariable UUID couponId, @RequestBody CouponRequest coupon) {
        var response = couponService.updateCoupon(couponId, coupon);
        return ResponseEntity.ok(ApiResponse.success("Update coupon successfully", response));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<ApiResponse<?>> getAllcoupons(@PathVariable UUID couponId) {
        couponService.deleteCouponById(couponId);
        return ResponseEntity.ok(ApiResponse.success("Delete coupon successfully", couponId));
    }
}
