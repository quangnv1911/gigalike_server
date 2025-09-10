package com.gigalike.payment.entity;

import com.gigalike.payment.base.BaseEntity;
import com.gigalike.payment.constant.PaymentStatus;
import com.gigalike.payment.constant.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Data
@SQLDelete(sql = "UPDATE users SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus = PaymentStatus.UNPAID;
}
