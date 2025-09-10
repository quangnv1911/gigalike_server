package com.gigalike.marketing.repository;

import com.gigalike.marketing.entity.Coupon;
import com.gigalike.marketing.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
