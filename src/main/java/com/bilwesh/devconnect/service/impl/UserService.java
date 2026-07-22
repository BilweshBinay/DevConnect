package com.bilwesh.devconnect.service.impl;

import com.bilwesh.devconnect.dto.UserLoginRequest;
import com.bilwesh.devconnect.dto.UserLoginResponse;
import com.bilwesh.devconnect.dto.UserRegistrationRequest;
import com.bilwesh.devconnect.dto.UserRegistrationResponse;
import jakarta.validation.Valid;


public interface UserService {
    public UserRegistrationResponse registerUser (UserRegistrationRequest request);
    UserLoginResponse loginUser(UserLoginRequest request);
}
