package com.gigalike.product.dto.data;

import com.gigalike.product.entity.Product;
import com.gigalike.product.entity.ProductDuration;
import com.gigalike.shared.constant.Duration;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDurationDto {
    UUID productDurationId;

    Duration duration;

    BigDecimal price;

    public static ProductDurationDto fromEntity(ProductDuration productDuration) {
        return ProductDurationDto.builder()
                .productDurationId(productDuration.getId())
                .duration(productDuration.getDuration())
                .price(productDuration.getPrice())
                .build();
    }
}
