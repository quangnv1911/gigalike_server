package com.gigalike.order.dto.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
}
