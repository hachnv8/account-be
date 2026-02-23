package com.hacheery.accountbe.security.service;

import com.hacheery.accountbe.security.entity.User;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(User user);
}
