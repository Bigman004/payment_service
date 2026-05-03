package com.example.paymentService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = -8150429406995016354L;

    private List<Unit> items;

    @Data
    public static class Unit implements Serializable {

        @Serial
        private static final long serialVersionUID = -3692560910846835558L;

        private String itemName;
        private Integer price;
        private String quantity;
        private String discount;
    }
}
