package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
