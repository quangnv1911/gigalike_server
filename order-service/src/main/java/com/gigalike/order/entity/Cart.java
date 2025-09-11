package com.gigalike.order.entity;

import com.gigalike.order.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@SQLDelete(sql = "UPDATE carts SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Cart extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    UUID userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> items = new ArrayList<>();

    @Column(name = "total_amount", precision = 10, scale = 2)
    BigDecimal totalAmount = BigDecimal.ZERO;
}
