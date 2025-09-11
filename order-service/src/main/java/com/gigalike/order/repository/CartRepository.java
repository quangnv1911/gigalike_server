package com.gigalike.order.repository;

import com.gigalike.order.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findFirstByUserId(UUID userId);

    Optional<Cart> findFirstById(UUID id);
}
