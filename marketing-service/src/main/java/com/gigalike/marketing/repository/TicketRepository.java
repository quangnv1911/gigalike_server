package com.gigalike.marketing.repository;

import com.gigalike.marketing.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TicketRepository extends MongoRepository<Ticket, UUID> {
}
