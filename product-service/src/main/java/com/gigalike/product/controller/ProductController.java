package com.gigalike.product.controller;

import com.gigalike.product.dto.request.ProductFilterRequest;
import com.gigalike.product.service.IProductService;
import com.gigalike.shared.constant.BigCategory;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    IProductService productService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getProductsByCate(@ModelAttribute ProductFilterRequest filterRequest) {
        var response = productService.getProductsByCate(filterRequest);
        return ResponseEntity.ok(ApiResponse.success("Get all product by cate successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getProductDetail(@PathVariable UUID id) {
        var response = productService.getProductDetail(id);
        return ResponseEntity.ok(ApiResponse.success("Get product detail successfully", response));
    }

}
