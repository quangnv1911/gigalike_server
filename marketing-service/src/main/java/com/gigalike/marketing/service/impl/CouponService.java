package com.gigalike.marketing.service.impl;

import com.gigalike.marketing.dto.request.CouponRequest;
import com.gigalike.marketing.dto.request.CouponSearchRequest;
import com.gigalike.marketing.dto.response.CouponResponse;
import com.gigalike.marketing.entity.Coupon;
import com.gigalike.marketing.repository.CouponRepository;
import com.gigalike.marketing.service.ICouponService;
import com.gigalike.shared.constant.SortDirection;
import com.gigalike.shared.dto.PageResponse;
import com.gigalike.shared.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponService implements ICouponService {
    CouponRepository couponRepository;

    @Override
    public PageResponse<CouponResponse> getCoupons(CouponSearchRequest couponSearchRequest) {
        Pageable pageable = PageRequest.of(
                couponSearchRequest.getPage(),
                couponSearchRequest.getSize(),
                Sort.by(
                        couponSearchRequest.getSortDirection() == SortDirection.ASC
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                        couponSearchRequest.getSortBy()
                )
        );
        Page<Coupon> coupons = couponRepository.findAll(pageable);

        return PageResponse.fromPage(coupons, CouponResponse::fromCoupon);
    }

    @Override
    public CouponResponse getCouponById(UUID couponId) {
        var coupon = findCouponById(couponId);
        return CouponResponse.fromCoupon(coupon);
    }

    @Override
    public CouponResponse createCoupon(CouponRequest newCoupon) {
        var coupon = CouponRequest.convertToCoupon(newCoupon);
        var couponResponse = CouponResponse.fromCoupon(couponRepository.save(coupon));
        log.info("Coupon created: {}", couponResponse);
        return couponResponse;
    }

    @Override
    public CouponResponse updateCoupon(UUID couponId, CouponRequest newCoupon) {
        var coupon = findCouponById(couponId);
        coupon.setName(newCoupon.getName());
        coupon.setMaxUsed(newCoupon.getMaxUsed());
        coupon.setValue(newCoupon.getValue());
        couponRepository.save(coupon);
        log.info("Coupon updated with id {}", coupon.getId());
        return CouponResponse.fromCoupon(coupon);
    }

    @Override
    public void deleteCouponById(UUID couponId) {
        var coupon = findCouponById(couponId);
        couponRepository.delete(coupon);
        log.info("Coupon with id {} has been deleted", couponId);
    }

    private Coupon findCouponById(UUID couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("Coupon not found"));
    }

}
