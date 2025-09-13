package com.gigalike.marketing.repository;

import com.gigalike.marketing.entity.Ticket;
import com.gigalike.marketing.entity.WebConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebConfigRepository extends MongoRepository<WebConfig, UUID> {
    Optional<WebConfig> findFirstByDomain(String domain);
}
