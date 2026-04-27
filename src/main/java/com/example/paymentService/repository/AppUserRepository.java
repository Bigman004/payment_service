package com.example.paymentService.repository;

import com.example.paymentService.Model.AppUser;
import com.example.paymentService.Model.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
