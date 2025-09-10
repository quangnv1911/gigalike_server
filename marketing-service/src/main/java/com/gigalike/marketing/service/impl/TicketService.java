package com.gigalike.marketing.service.impl;

import com.gigalike.marketing.dto.request.TicketRequest;
import com.gigalike.marketing.dto.request.TicketSearchRequest;
import com.gigalike.marketing.dto.response.CouponResponse;
import com.gigalike.marketing.dto.response.TicketResponse;
import com.gigalike.marketing.entity.Coupon;
import com.gigalike.marketing.entity.Ticket;
import com.gigalike.marketing.repository.TicketRepository;
import com.gigalike.marketing.service.ITicketService;
import com.gigalike.shared.constant.SortDirection;
import com.gigalike.shared.dto.PageResponse;
import com.gigalike.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService implements ITicketService {
    TicketRepository ticketRepository;


    @Override
    public PageResponse<TicketResponse> getTickets(TicketSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(
                        request.getSortDirection() == SortDirection.ASC
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                        request.getSortBy()
                )
        );
        Page<Ticket> tickets = ticketRepository.findAll(pageable);

        return PageResponse.fromPage(tickets, TicketResponse::fromTicket);
    }

    @Override
    public TicketResponse getTicketById(UUID ticketId) {
        return TicketResponse.fromTicket(
                findTicketById(ticketId)
        );
    }

    @Override
    @Transactional
    public TicketResponse createTicket(TicketRequest ticket) {
        var newTicket = TicketRequest.convertTicket(ticket);
        var response = ticketRepository.save(newTicket);
        log.info("Ticket created: {}", newTicket);
        return TicketResponse.fromTicket(response);
    }

    @Override
    @Transactional
    public TicketResponse updateTicket(UUID ticketId, TicketRequest ticketRequest) {
        var ticket = findTicketById(ticketId);
        ticket.setDescription(ticket.getDescription());
        ticket.setName(ticket.getName());
        ticket.setType(ticket.getType());
        ticket.setStatus(ticket.getStatus());
        ticket.setImage(ticket.getImage());
        ticketRepository.save(ticket);
        log.info("Updated ticket with id {}", ticketId);
        return TicketResponse.fromTicket(ticket);
    }

    @Override
    @Transactional
    public void deleteTicketById(UUID ticketId) {
        var ticket = findTicketById(ticketId);
        ticketRepository.delete(ticket);
        log.info("Deleted ticket with id={}", ticketId);
    }

    private Ticket findTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
    }
}
