package com.example.paymentService.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Value("${spring.security.user.password}")
    private String internalApiKey;
    @Override
    public void apply(RequestTemplate template) {
        String credentials = "user:" + internalApiKey;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        template.header("Authorization",  "Basic " + encodedCredentials );
    }
}
