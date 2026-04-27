package com.example.paymentService.dto;

import com.example.paymentService.Model.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class PaymentVerificationDto {

    @JsonProperty
    private AppUser appUser;

    private String reference;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String ipAddress;
    private String pricingPlan;
    private Date createdOn = new Date();

    public PaymentVerificationDto(){}

}
