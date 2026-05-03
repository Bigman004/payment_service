package com.example.paymentService.feign;

import com.example.paymentService.Model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mailing-service")
public interface EmailInterface {
    @PostMapping("/send_reciept")
    ResponseEntity<String> sendReceipt(@RequestBody Order order);
}
