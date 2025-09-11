package com.gigalike.product.controller;

import com.gigalike.product.service.ICategoryService;
import com.gigalike.shared.constant.BigCategory;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;

    @GetMapping("/{bigCategory}")
    public ResponseEntity<ApiResponse<?>> getAllCateByBigCate(@PathVariable BigCategory bigCategory) {
        var response = categoryService.getAllCategories(bigCategory);
        return ResponseEntity.ok(ApiResponse.success("Get all categories by Big Category successfully", response));
    }
}
