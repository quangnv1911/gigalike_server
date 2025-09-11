package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.Duration;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "product_duration")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE product_duration SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class ProductDuration extends BaseEntity {
    @Column(name = "duration", nullable = false)
    @Enumerated(EnumType.STRING)
    Duration duration;

    @Column(name = "price")
    BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
}
