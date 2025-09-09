package com.gigalike.payment.repository;

import com.gigalike.payment.entity.PaymentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentConfigRepository extends JpaRepository<PaymentConfig, UUID> {

}
