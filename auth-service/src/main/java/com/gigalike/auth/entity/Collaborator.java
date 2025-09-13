package com.gigalike.auth.entity;

import com.gigalike.auth.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "collaborators")
@SQLDelete(sql = "UPDATE collaborators SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Collaborator extends BaseEntity {
    @Column(name = "parent_id")
    UUID parentId;

    @Column(name = "user_id")
    UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    String domain;

}
