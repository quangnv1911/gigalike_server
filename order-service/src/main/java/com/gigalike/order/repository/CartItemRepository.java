package com.gigalike.order.repository;

import com.gigalike.order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByCart_Id(UUID cartId);
}
