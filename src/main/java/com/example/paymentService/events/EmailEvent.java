package com.example.paymentService.events;

import com.example.paymentService.Model.Order;

public record EmailEvent(
        Order order
) {}
