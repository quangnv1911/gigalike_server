package com.gigalike.marketing.dto.response;

import com.gigalike.marketing.constant.TicketStatus;
import com.gigalike.marketing.entity.Ticket;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    UUID id;
    String name;

    String description;

    String image;

    @Nullable
    String response;

    @Column(name = "image-response")
    @Nullable
    String imageResponse;

    TicketStatus status;

    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;

    public static TicketResponse fromTicket(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .name(ticket.getName())
                .description(ticket.getDescription())
                .image(ticket.getImage())
                .response(ticket.getResponse())
                .imageResponse(ticket.getImageResponse())
                .status(ticket.getStatus())
                .createdBy(ticket.getCreatedBy())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
