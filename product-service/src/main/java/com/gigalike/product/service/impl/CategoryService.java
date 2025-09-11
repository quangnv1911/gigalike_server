package com.gigalike.product.service.impl;

import com.gigalike.product.dto.data.CategoryDto;
import com.gigalike.product.repository.CategoryRepository;
import com.gigalike.product.service.ICategoryService;
import com.gigalike.shared.constant.BigCategory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> getAllCategories(BigCategory bigCategory) {
        var response = categoryRepository.findAllByBigCategory(bigCategory);
        return response.stream().map(CategoryDto::fromEntity).toList();
    }
}
