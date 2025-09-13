package com.gigalike.product.service.impl;

import com.gigalike.product.dto.data.ProductDto;
import com.gigalike.product.dto.request.ProductFilterRequest;
import com.gigalike.product.dto.response.CollaboratorResponse;
import com.gigalike.product.entity.Product;
import com.gigalike.product.feign.AuthClient;
import com.gigalike.product.repository.ProductRepository;
import com.gigalike.product.repository.specification.ProductSpecifications;
import com.gigalike.product.service.IProductService;
import com.gigalike.shared.constant.SortDirection;
import com.gigalike.shared.dto.ApiResponse;
import com.gigalike.shared.dto.PageResponse;
import com.gigalike.shared.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {
    ProductRepository productRepository;
    AuthClient authClient;

    @Override
    public PageResponse<ProductDto> getProductsByCate(ProductFilterRequest filterRequest) {
        var collaboratorResponse = authClient.getCollaboratorByHostName(filterRequest.getHostName());
        Pageable pageable = PageRequest.of(
                filterRequest.getPage(),
                filterRequest.getSize(),
                Sort.by(
                        filterRequest.getSortDirection() == SortDirection.ASC
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                        filterRequest.getSortBy()
                )
        );
        Page<Product> products = productRepository.findAll(ProductSpecifications.filter(filterRequest, collaboratorResponse.getData().getId()), pageable);

        return PageResponse.fromPage(products, ProductDto::fromProduct);
    }

    @Override
    public ProductDto getProductDetail(UUID id) {
        var response = productRepository.findById(id);
        if (response.isEmpty()) {
            log.warn("Product not found with id {}", id);
            throw new NotFoundException(String.format("Product %s not found", id));
        }
        return ProductDto.fromProduct(response.get());
    }
}
