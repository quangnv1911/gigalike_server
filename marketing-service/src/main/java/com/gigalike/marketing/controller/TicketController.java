package com.gigalike.marketing.controller;

import com.gigalike.marketing.dto.request.TicketRequest;
import com.gigalike.marketing.dto.request.TicketSearchRequest;
import com.gigalike.marketing.service.ITicketService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {
    ITicketService ticketService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllTickets(@ModelAttribute TicketSearchRequest request) {
        var response = ticketService.getTickets(request);
        return ResponseEntity.ok(ApiResponse.success("Get all tickets successfully", response));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<?>> getTicketDetail(@PathVariable UUID ticketId) {
        var response = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ApiResponse.success("Get ticket detail successfully", response));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> createTicket(@RequestBody TicketRequest ticket) {
        var response = ticketService.createTicket(ticket);
        return ResponseEntity.ok(ApiResponse.success("Create ticket successfully", response));
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<?>> updateTicket(@PathVariable UUID ticketId, @RequestBody TicketRequest ticket) {
        var response = ticketService.updateTicket(ticketId, ticket);
        return ResponseEntity.ok(ApiResponse.success("Update ticket successfully", response));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<?>> deleteTicketId(@PathVariable UUID ticketId) {
        ticketService.deleteTicketById(ticketId);
        return ResponseEntity.ok(ApiResponse.success("Delete ticket successfully", ticketId));
    }
}
