package com.gigalike.marketing.entity;

import com.gigalike.marketing.base.BaseEntity;
import com.gigalike.marketing.constant.TicketStatus;
import com.gigalike.marketing.constant.TicketType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(value = "tickets")
@Data
@SQLDelete(sql = "UPDATE tickets SET is_delete = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket extends BaseEntity {

    String name;

    String description;

    String image;

    @Nullable
    String response;

    @Column(name = "image-response")
    @Nullable
    String imageResponse;

    @Enumerated(EnumType.STRING)
    TicketStatus status;

    @Enumerated(EnumType.STRING)
    TicketType type;
}
