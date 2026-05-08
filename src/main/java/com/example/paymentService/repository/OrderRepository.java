package com.example.paymentService.repository;

import com.example.paymentService.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByReference(String reference);
    public boolean existsByReference(String reference);

    boolean existsByEmail(String email);
}
