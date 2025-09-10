package com.gigalike.payment.entity;

import com.gigalike.payment.base.BaseEntity;
import com.gigalike.payment.constant.PaymentType;
import com.gigalike.payment.dto.data.CassoConfigDto;
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
@Table(name = "users")
@Data
@SQLDelete(sql = "UPDATE users SET is_delete = true WHERE id = ?")
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
