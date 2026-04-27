package com.example.paymentService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Item implements Serializable {

    private List<Unit> items;

    @Data
    public static class Unit implements Serializable {
        private String itemName;
        private Integer price;
        private String quantity;
        private String discount;
    }
}
