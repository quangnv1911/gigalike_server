package com.gigalike.marketing.dto.request;

import com.gigalike.marketing.constant.TicketStatus;
import com.gigalike.marketing.constant.TicketType;
import com.gigalike.marketing.entity.Ticket;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRequest {
    String name;

    String description;

    String image;

    TicketType type;

    @Builder.Default
    TicketStatus status = TicketStatus.OPEN;

    public static Ticket convertTicket(TicketRequest ticketRequest)
    {
        return Ticket.builder()
                .name(ticketRequest.name)
                .description(ticketRequest.description)
                .image(ticketRequest.image)
                .type(ticketRequest.type)
                .build();
    }
}
