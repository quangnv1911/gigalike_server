package com.gigalike.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductRequest {
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String name;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    private String category;

    private String status;

    private Integer stockQuantity;

    private Boolean isDigital;

    private List<String> imageUrls;

    private List<String> features;
}
