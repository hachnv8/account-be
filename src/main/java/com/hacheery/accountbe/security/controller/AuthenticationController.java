package com.hacheery.accountbe.security.controller;

import com.hacheery.accountbe.dto.VerifyOtpRequest;
import com.hacheery.accountbe.security.payload.AuthenticationRequest;
import com.hacheery.accountbe.security.payload.AuthenticationResponse;
import com.hacheery.accountbe.security.payload.RegisterRequest;
import com.hacheery.accountbe.security.repository.UserRepository;
import com.hacheery.accountbe.security.service.impl.AuthenticationServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<@NonNull AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthenticationResponse> verifyOtp(@RequestBody VerifyOtpRequest req) {
        AuthenticationResponse response = authenticationService.verifyOtp(req);
        return ResponseEntity.ok(response);
    }

}
