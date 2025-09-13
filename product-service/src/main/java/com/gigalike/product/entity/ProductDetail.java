package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.Duration;
import com.gigalike.product.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product_detail")
@SQLDelete(sql = "UPDATE product_detail SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class ProductDetail extends BaseEntity {
    String data;

    @Column(name = "duration")
    @Enumerated(EnumType.STRING)
    Duration duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    ProductStatus status = ProductStatus.NotPurchased;

    @ManyToOne
    @JoinColumn(name = "product_package_id", nullable = false)
    ProductPackage productPackage;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "order_detail_id", nullable = false)
    UUID orderDetailId;
}