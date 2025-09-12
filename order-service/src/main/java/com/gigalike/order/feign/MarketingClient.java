package com.gigalike.order.feign;

import com.gigalike.order.dto.data.CouponResponse;
import com.gigalike.order.dto.data.UpdateUserAmount;
import com.gigalike.shared.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "marketing-service")
public interface MarketingClient {
    @GetMapping("/coupons/code/{couponCode}")
    ApiResponse<CouponResponse> getCouponDetailByCouponCode(@PathVariable("couponCode") String couponCode);
}
