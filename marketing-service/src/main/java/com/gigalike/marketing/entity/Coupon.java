package com.gigalike.marketing.entity;

import com.gigalike.marketing.base.BaseEntity;
import com.gigalike.marketing.constant.CouponType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
@Data
@SQLDelete(sql = "UPDATE tickets SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon extends BaseEntity {
    @Column(unique = true, nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String code;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    CouponType type =  CouponType.FIXED_AMOUNT;

    @Builder.Default
    int value = 0;

    @Builder.Default
    int totalUsed = 0;

    @Builder.Default
    int maxUsed = 0;

    @Builder.Default
    LocalDateTime expiredIn = LocalDateTime.now();
}
