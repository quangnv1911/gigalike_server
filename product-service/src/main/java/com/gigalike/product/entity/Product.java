package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.ProductType;
import com.gigalike.shared.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Product extends BaseEntity {
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "images", columnDefinition = "json")
    @Convert(converter = StringListConverter.class)
    List<String> images;

    @Column(name = "description", columnDefinition = "Text")
    String description;

    @Column(name = "total_sold")
    @Builder.Default
    Integer totalSold = 0;

    @Column(name = "stock_quantity")
    @Builder.Default
    Integer stockQuantity = 0;

    @Column(name = "product_type")
    ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    Category category;

    @Column(name = "collaborator_id")
    UUID collaboratorId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductPackage> packages;
}