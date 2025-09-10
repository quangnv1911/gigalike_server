package com.gigalike.product.dto;

import com.gigalike.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String status;
    private Integer stockQuantity;
    private Boolean isDigital;
    private List<String> imageUrls;
    private List<String> features;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductDto fromProduct(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory().getName())
                .stockQuantity(product.getStockQuantity())
                .imageUrls(product.getImages())
                .createdBy(product.getCreatedBy())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
