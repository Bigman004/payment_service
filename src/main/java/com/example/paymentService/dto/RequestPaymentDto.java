package com.example.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class RequestPaymentDto {
    private String reference;

    private String paidAt;
    private Integer amount;

    public RequestPaymentDto(){}

}
