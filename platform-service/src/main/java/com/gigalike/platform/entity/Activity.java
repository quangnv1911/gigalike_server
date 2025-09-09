package com.gigalike.platform.entity;

import com.gigalike.platform.base.BaseEntity;
import com.gigalike.shared.constant.ActionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseEntity {
    @Column(name = "meta_data")
    String metaData;

    @Column(name = "status")
    String status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ActionType type;
}
