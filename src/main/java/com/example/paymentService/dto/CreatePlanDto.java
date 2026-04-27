package com.example.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class CreatePlanDto {
    @JsonProperty
    private String name;

    private String interval;
    private Integer amount;

    public CreatePlanDto(){}

}
