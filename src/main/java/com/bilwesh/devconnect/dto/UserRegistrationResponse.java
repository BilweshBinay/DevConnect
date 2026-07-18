package com.bilwesh.devconnect.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserRegistrationResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
}
