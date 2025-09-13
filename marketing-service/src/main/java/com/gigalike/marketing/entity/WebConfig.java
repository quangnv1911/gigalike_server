package com.gigalike.marketing.entity;

import com.gigalike.marketing.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Document(value  = "web_configs")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebConfig extends BaseEntity {
    @Column(name = "user_id")
    UUID userId;

    @Column(name = "parent_id")
    UUID parentId;

    @Column(name = "web_token")
    String webToken;

    String domain;

    BigDecimal price;
}
