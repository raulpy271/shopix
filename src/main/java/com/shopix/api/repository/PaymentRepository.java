package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
