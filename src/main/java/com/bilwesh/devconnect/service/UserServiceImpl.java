package com.bilwesh.devconnect.service;

import com.bilwesh.devconnect.dto.UserRegistrationRequest;
import com.bilwesh.devconnect.dto.UserRegistrationResponse;
import com.bilwesh.devconnect.entity.UserEntity;
import com.bilwesh.devconnect.exception.ResourceAlreadyExistsException;
import com.bilwesh.devconnect.repository.UserRepository;
import com.bilwesh.devconnect.service.impl.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());

        userEntity.setPassword(request.getPassword());
        userEntity.setFullName(request.getFullName());

        UserEntity savedUser = userRepository.save(userEntity);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(savedUser.getUserid());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setFullName(savedUser.getFullName());
        response.setCreatedAt(savedUser.getCreatedAt());

        return response;
    }
}
