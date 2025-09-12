package com.gigalike.marketing.entity;

import com.gigalike.marketing.base.BaseEntity;
import com.gigalike.shared.constant.CouponType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(value  = "coupons")
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
    BigDecimal value = BigDecimal.ZERO;

    @Builder.Default
    int totalUsed = 0;

    @Nullable
    Integer maxUsed;

    @Nullable
    LocalDateTime expiredIn;
}
