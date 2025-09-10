package com.gigalike.order.entity;

import com.gigalike.order.base.BaseEntity;
import com.gigalike.shared.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "carts")
public class Cart extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "product_items", columnDefinition = "json")
    @Convert(converter = StringListConverter.class)
    List<UUID> productItems;

    @Column(name = "total_amount", precision = 10, scale = 2)
    BigDecimal totalAmount = BigDecimal.ZERO;
}
