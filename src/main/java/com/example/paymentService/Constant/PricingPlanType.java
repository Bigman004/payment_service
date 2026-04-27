package com.example.paymentService.Constant;

public enum PricingPlanType {

    BASIC("Basic"),
    STANDARD("Standard"),
    PREMIUM("Premium");
    private String value;
    PricingPlanType(String value) {
        this.value = value;
    }
}
