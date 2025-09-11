package com.gigalike.payment.entity;

import com.gigalike.payment.base.BaseEntity;
import com.gigalike.payment.constant.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_configs")
@Data
@SQLDelete(sql = "UPDATE payment_configs SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentConfig extends BaseEntity {
    String bankName;

    String cardNumber;

    String bankBin;
    String userName;

    boolean enabled = true;

    @Lob
    String config;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

}
