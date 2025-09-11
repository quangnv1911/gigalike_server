package com.gigalike.order.entity;

import com.gigalike.order.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
@SQLDelete(sql = "UPDATE order_items SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Column(name = "product_id", nullable = false)
    UUID productId;

    @Column(name = "product_duration_id", nullable = false)
    UUID productDuration;

    @Column(nullable = false)
    Integer quantity = 0;

    @Column(name = "order_info")
    String orderInfo;

}
