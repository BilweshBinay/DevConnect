package com.bilwesh.devconnect.service.impl;

import com.bilwesh.devconnect.dto.UserRegistrationRequest;
import com.bilwesh.devconnect.dto.UserRegistrationResponse;


public interface UserService {
    public UserRegistrationResponse registerUser (UserRegistrationRequest request);
}
