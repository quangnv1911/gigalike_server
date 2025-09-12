package com.gigalike.platform.entity;

import com.gigalike.platform.base.BaseEntity;
import com.gigalike.shared.constant.ActionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(value = "activity")
@Data
@SQLDelete(sql = "UPDATE activity SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Activity extends BaseEntity {
    @Column(name = "meta_data")
    String metaData;

    @Column(name = "status")
    String status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ActionType type;
}
