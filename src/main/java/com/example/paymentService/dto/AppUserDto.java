package com.example.paymentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long id;

    private String username;
    private String name;
    private String email;
    private String address;
    private Date creationDate;
}
