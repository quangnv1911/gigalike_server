package com.gigalike.product.dto.data;

import com.gigalike.shared.constant.Duration;
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

    public static ProductDurationDto fromEntity(ProductPlan productPlan) {
        return ProductDurationDto.builder()
                .productDurationId(productPlan.getId())
                .duration(productPlan.getDuration())
                .price(productPlan.getPrice())
                .build();
    }
}
