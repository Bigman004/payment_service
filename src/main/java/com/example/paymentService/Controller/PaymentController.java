package com.example.paymentService.Controller;

import com.example.paymentService.Model.PaymentPaystack;
import com.example.paymentService.dto.AppUserDto;
import com.example.paymentService.dto.InitializePaymentDto;
import com.example.paymentService.dto.OrderDto;
import com.example.paymentService.response.PaymentVerificationResponse;
import com.example.paymentService.service.OrderService;
import com.example.paymentService.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/welcome")
    public String welcome(@RequestHeader HttpHeaders headers) {

        System.out.println(headers.containsHeader("Authorization"));
        System.out.println(headers.getFirst("Authorization"));
        return "Welcome to PaymentService";
    }
    @GetMapping("/initialize_payment")
    public ResponseEntity<?> intializePayemnt() {

        return new ResponseEntity<>(OrderDto.builder().build(), HttpStatus.OK);
    }
    @GetMapping("/payment")
    public ResponseEntity<?> payment() {
        return new ResponseEntity<>(InitializePaymentDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody OrderDto order) {
        try{
            return new ResponseEntity<>(
                    paymentService.initializePayment(order),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/create_user")
    public ResponseEntity<?> createUser() {
        return new ResponseEntity<>(AppUserDto.builder().build(), HttpStatus.OK);

    }
    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody AppUserDto appUserDto) {
        paymentService.createUser(appUserDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationResponse paymentVerificationResponse) {
        try {
            return new ResponseEntity<>(paymentService.verifyPayment(paymentVerificationResponse), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/check-status")
    public ResponseEntity<?> checkPayments(@RequestParam String email){
        ListWrapper listWrapper = new ListWrapper(paymentService.findByEmail(email));
        return new ResponseEntity<>(listWrapper ,HttpStatus.OK);
    }
    @GetMapping("/order/{l}")
    public ResponseEntity<?> order(@PathVariable long l) {
        return new ResponseEntity<>(orderService.getOrder((long) l), HttpStatus.OK);
    }
    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
    public class ListWrapper{
        public List<PaymentPaystack> list;
        public ListWrapper(List<PaymentPaystack> list){
            this.list = list;}

        public List<PaymentPaystack> getList() {

            return list;
        }
    }
}
