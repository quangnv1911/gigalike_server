package com.gigalike.product.service;

import com.gigalike.product.dto.data.ProductDto;
import com.gigalike.product.dto.request.ProductFilterRequest;
import com.gigalike.shared.dto.PageResponse;

import java.util.UUID;

public interface IProductService {
    PageResponse<ProductDto> getProductsByCate(ProductFilterRequest filterRequest);
    ProductDto getProductDetail(UUID id);
}
