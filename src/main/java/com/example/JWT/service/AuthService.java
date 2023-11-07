package com.example.JWT.service;

import com.example.JWT.model.dto.UserDTO;
import com.example.JWT.model.entity.User;
import com.example.JWT.model.mapper.UserMapper;
import com.example.JWT.model.response.AuthenticationResponse;
import com.example.JWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final JwtService jwtService;

    public AuthenticationResponse registration(UserDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = repository.save(userMapper.convertToEntity(request));
        String jwtToken = jwtService.generateToken(savedUser);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }
}
