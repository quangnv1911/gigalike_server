package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.BigCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Category extends BaseEntity {
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "image")
    String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Product> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "big_category", nullable = false)
    BigCategory bigCategory;
}
