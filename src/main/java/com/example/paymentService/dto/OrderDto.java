package com.example.paymentService.dto;

import com.example.paymentService.Model.Item;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long code;
    private String interval;
    private Item item;
    private String email;
    private String reference;
    private String amount;

    public Integer getAmount() {
        int count = 0;
        for(Item.Unit unit: item.getItems()){
            count += (unit.getPrice()*100) * Integer.parseInt(unit.getQuantity());
        }
        return count;
    }
}
