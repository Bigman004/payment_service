package com.example.paymentService.Wrapper;

import com.example.paymentService.Model.Order;
import com.example.paymentService.dto.OrderDto;

public class ModelWrappers {

    static public Order MapperToPlan(OrderDto planDto){
        return Order.builder()
                .id(planDto.getId())
                .code(planDto.getCode())
                .amount(planDto.getAmount())
                .item(planDto.getItem())
                .build();

    }
}
