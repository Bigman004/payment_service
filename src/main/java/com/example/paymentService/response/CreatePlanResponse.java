package com.example.paymentService.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        private String name;
        private String amount;
        private String interval;
        private String integration;

        @JsonProperty("plan_code")
        private String planCode;

        @JsonProperty("send_invoices")
        private String sendInvoices;

        @JsonProperty("send_sms")
        private String sendSms;

        private String currency;
        private String id;
        private String createdAt;
        private String updatedAt;
    }
}