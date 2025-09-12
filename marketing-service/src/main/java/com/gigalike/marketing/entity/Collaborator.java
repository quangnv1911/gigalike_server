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
@Document(value  = "collaborators")
@Data
@SQLDelete(sql = "UPDATE collaborators SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Collaborator extends BaseEntity {

    @Column(name = "user_id")
    UUID userId;

    @Column(name = "parent_id")
    UUID parentId;

    BigDecimal price;
}
