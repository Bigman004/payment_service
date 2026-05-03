package com.example.paymentService.repository;

import com.example.paymentService.Model.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentPaystack, Long> {
    boolean existsByReference(String reference);
}
