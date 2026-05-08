package com.example.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Builder
@Data
@AllArgsConstructor
public class RequestPaymentDto {
    private String reference;

    private String paidAt;
    private BigDecimal amount;

    public RequestPaymentDto(){}

}
