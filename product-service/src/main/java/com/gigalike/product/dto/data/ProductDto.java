package com.gigalike.product.dto.data;

import com.gigalike.product.entity.Category;
import com.gigalike.product.entity.Product;
import com.gigalike.product.entity.ProductDuration;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    UUID id;
    String name;
    String description;
    BigDecimal price;
    String categoryName;
    Integer stockQuantity;
    List<String> imageUrls;
    LocalDateTime updatedAt;

    List<ProductDurationDto> productDurations;

    public static ProductDto fromProduct(Product product) {
        List<ProductDurationDto> durationDtos = Optional.ofNullable(product.getProductDurations())
                .orElse(Collections.emptyList())
                .stream()
                .map(ProductDurationDto::fromEntity)
                .toList();

        String categoryName = Optional.ofNullable(product.getCategory())
                .map(Category::getName)
                .orElse(null);

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryName(categoryName)
                .stockQuantity(product.getStockQuantity())
                .imageUrls(Optional.ofNullable(product.getImages()).orElse(Collections.emptyList()))
                .updatedAt(product.getUpdatedAt())
                .productDurations(durationDtos)
                .build();
    }
}
