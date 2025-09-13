package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.Duration;
import com.gigalike.shared.constant.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product_packages")
@SQLDelete(sql = "UPDATE product_packages SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class ProductPackage extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    ProductType productType; // CRON_JOB, MMO_RESOURCE, SERVICE

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "duration")
    @Enumerated(EnumType.STRING)
    Duration duration; // MONTHLY, YEARLY, NONE (nếu không có)

    @OneToMany(mappedBy = "productPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ProductDetail> productDetails;

    @Column(name = "price", nullable = false)
    BigDecimal price;
}
