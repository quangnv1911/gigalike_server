package com.gigalike.product.entity;

import com.gigalike.product.base.BaseEntity;
import com.gigalike.shared.constant.Duration;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "product_duration")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
