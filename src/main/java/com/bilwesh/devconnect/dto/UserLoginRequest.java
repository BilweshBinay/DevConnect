package com.bilwesh.devconnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotBlank(message = "Please Enter the email address.")
    @Email(message = "Please Enter the valid email address.")
    private String email;

    @NotBlank(message = "Please Enter the password.")
    private String password;
}
