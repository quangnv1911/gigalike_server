package com.gigalike.product.repository.specification;

import com.gigalike.product.dto.request.ProductFilterRequest;
import com.gigalike.product.entity.Category;
import com.gigalike.product.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecifications {

    public static Specification<Product> filter(ProductFilterRequest filter, UUID collaboratorId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Product, Category> categoryJoin = root.join("category", JoinType.LEFT);

            // Filter theo category object
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(categoryJoin.get("id"), filter.getCategoryId()));
            }

            if (collaboratorId!= null) {
                predicates.add(cb.equal(categoryJoin.get("collaborator_id"), collaboratorId));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if (filter.getSearchTerm() != null && !filter.getSearchTerm().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getSearchTerm().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

