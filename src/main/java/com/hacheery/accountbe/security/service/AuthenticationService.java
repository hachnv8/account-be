package com.hacheery.accountbe.security.service;


import com.hacheery.accountbe.security.payload.AuthenticationRequest;
import com.hacheery.accountbe.security.payload.AuthenticationResponse;
import com.hacheery.accountbe.security.payload.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse verifyOtp(com.hacheery.accountbe.dto.VerifyOtpRequest request);
}
