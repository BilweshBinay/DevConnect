package com.bilwesh.devconnect.service;

import com.bilwesh.devconnect.dto.UserLoginRequest;
import com.bilwesh.devconnect.dto.UserLoginResponse;
import com.bilwesh.devconnect.dto.UserRegistrationRequest;
import com.bilwesh.devconnect.dto.UserRegistrationResponse;
import com.bilwesh.devconnect.entity.UserEntity;
import com.bilwesh.devconnect.exception.InvalidCredentialsException;
import com.bilwesh.devconnect.exception.ResourceAlreadyExistsException;
import com.bilwesh.devconnect.exception.UserNotFoundException;
import com.bilwesh.devconnect.repository.UserRepository;
import com.bilwesh.devconnect.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
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

    @Override
    public UserLoginResponse loginUser(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getUserid());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());

        return response;
    }
}
