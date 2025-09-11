package com.gigalike.product.repository;

import com.gigalike.product.entity.Category;
import com.gigalike.shared.constant.BigCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findAllByBigCategory(BigCategory bigCategory);
}
