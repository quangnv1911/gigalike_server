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
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(value = "payments")
@Data
@SQLDelete(sql = "UPDATE payments SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {
    BigDecimal amount;

    String description;

    // Mã giao dịch từ phía ngân hàng
    String reference;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus = PaymentStatus.UNPAID;
}
