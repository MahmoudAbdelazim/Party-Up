package com.partyup.controller;

import com.partyup.payload.LoginDto;
import com.partyup.payload.LoginResponseDto;
import com.partyup.payload.SignUpDto;
import com.partyup.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.signin(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {
        return authService.register(signUpDto);
    }
}