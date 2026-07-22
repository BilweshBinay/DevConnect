package com.bilwesh.devconnect.dto;

import lombok.Data;

@Data
public class UserLoginResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String message = "Login successful!";
}
