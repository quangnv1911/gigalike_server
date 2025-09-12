package com.gigalike.order.feign;

import com.gigalike.order.dto.data.ProductDto;
import com.gigalike.shared.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/{productId}")
    ApiResponse<ProductDto> getProductDetail(@PathVariable("productId") UUID productId);
}
