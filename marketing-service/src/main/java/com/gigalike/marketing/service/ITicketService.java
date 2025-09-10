package com.gigalike.marketing.service;

import com.gigalike.marketing.dto.request.TicketRequest;
import com.gigalike.marketing.dto.request.TicketSearchRequest;
import com.gigalike.marketing.dto.response.TicketResponse;
import com.gigalike.marketing.entity.Ticket;
import com.gigalike.shared.dto.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ITicketService {
    PageResponse<TicketResponse> getTickets(TicketSearchRequest request);
    TicketResponse getTicketById(UUID ticketId);
    TicketResponse createTicket(TicketRequest ticket);
    TicketResponse updateTicket(UUID ticketId,TicketRequest ticketRequest);
    void deleteTicketById(UUID ticketId);

}
