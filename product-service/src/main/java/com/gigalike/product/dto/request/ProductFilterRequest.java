package com.gigalike.product.dto.request;

import com.gigalike.shared.dto.PageRequestCustom;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilterRequest extends PageRequestCustom {
    private UUID categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String searchTerm;
}
