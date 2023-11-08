package com.example.JWT.controller;

import com.example.JWT.model.dto.AuthRequest;
import com.example.JWT.model.dto.UserDTO;
import com.example.JWT.model.response.AuthenticationResponse;
import com.example.JWT.model.response.ValidationResponse;
import com.example.JWT.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class JwtController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.registration(request));
    }

    @GetMapping("/generation")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(authService.generateToken(request.getUsername()));
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validation")
    public ResponseEntity<ValidationResponse> validation(@RequestHeader("Authorization") String authorization, @RequestHeader String username) {
        return ResponseEntity.ok(ValidationResponse.builder()
                .valid(authService.validToken(authorization, username))
                .build());
    }
}
