package com.example.paymentService.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.paymentService.Model.Order;
import com.example.paymentService.dto.OrderDto;
import com.example.paymentService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(OrderDto order){
        orderRepository.save(
                Order.builder()
                        .amount(order.getAmount())
                        .item(order.getItem())
                        .code(generateOrderCode())
                        .email(order.getEmail())
                        .reference(order.getReference())
                        .build()
        );

    }
    private Long generateOrderCode(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId();
    }
}
