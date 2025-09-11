package com.gigalike.product.service;

import com.gigalike.product.dto.data.CategoryDto;
import com.gigalike.shared.constant.BigCategory;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAllCategories(BigCategory bigCategory);
}
