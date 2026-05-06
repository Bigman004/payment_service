package com.example.paymentService.feign;

import com.example.paymentService.events.EmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailEventListener {
    @Autowired
    private EmailInterface email;

    @EventListener
    @Async
    public void sendEmail(EmailEvent emailEvent){
        log.info("sendEmail running on thread: {}", Thread.currentThread().getName());
        log.info("sending email");
        try {
            ResponseEntity<String> response = email.sendReceipt(emailEvent.order());
            if(response.getStatusCode().equals(HttpStatus.OK))
                log.info("email sent to owner");
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
