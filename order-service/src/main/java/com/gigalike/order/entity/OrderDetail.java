package com.gigalike.order.entity;

import com.gigalike.order.base.BaseEntity;
import com.gigalike.shared.constant.BigCategory;
import com.gigalike.shared.constant.Duration;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


@Entity
@Table(name = "order_detail")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail extends BaseEntity {

    @Column(name = "mmo_resource_id")
    UUID mmoResourceId;

    @Column(name = "cronjob_key")
    String cronjobKey;

    @Column(name= "status")
    @Enumerated(EnumType.STRING)
    Order.OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    BigCategory category;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    Integer quantity = 0;

    @Column(name = "duration")
    @Enumerated(EnumType.STRING)
    Duration duration;

    @JoinColumn(name = "order_id", nullable = false)
    UUID orderId;
}