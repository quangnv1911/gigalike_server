package com.gigalike.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilterRequest {
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String searchTerm;
    private int page = 0;
    private int size = 20;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
