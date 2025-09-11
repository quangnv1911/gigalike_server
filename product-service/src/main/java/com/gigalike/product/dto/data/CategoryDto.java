package com.gigalike.product.dto.data;

import com.gigalike.product.entity.Category;
import com.gigalike.shared.constant.BigCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryDto {
    UUID id;
    String name;
    String description;
    String image;
    @Enumerated(EnumType.STRING)
    BigCategory bigCategory;

    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .bigCategory(category.getBigCategory())
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .build();
    }
}
