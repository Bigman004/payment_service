package com.example.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitializePaymentDto {
    private Integer amount;
    private String email;
    private String currency;
    private String channel;

    @JsonProperty("callback_url")
    private String callbackUrl;


}
