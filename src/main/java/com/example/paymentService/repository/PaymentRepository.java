package com.example.paymentService.repository;

import com.example.paymentService.Model.AppUser;
import com.example.paymentService.Model.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentPaystack, Long> {
    boolean existsByReference(String reference);

    List<PaymentPaystack> findAllByAppUser(AppUser appUser);
}
