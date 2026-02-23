package com.hacheery.accountbe.security.service.impl;

import com.hacheery.accountbe.dto.VerifyOtpRequest;
import com.hacheery.accountbe.exception.AppException;
import com.hacheery.accountbe.exception.ErrorCode;
import com.hacheery.accountbe.security.entity.Role;
import com.hacheery.accountbe.security.entity.User;
import com.hacheery.accountbe.security.mapper.UserMapper;
import com.hacheery.accountbe.security.payload.AuthenticationRequest;
import com.hacheery.accountbe.security.payload.AuthenticationResponse;
import com.hacheery.accountbe.security.payload.RegisterRequest;
import com.hacheery.accountbe.security.repository.UserRepository;
import com.hacheery.accountbe.security.service.AuthenticationService;
import com.hacheery.accountbe.service.MailService;
import com.hacheery.accountbe.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.ADMIN); // bạn có thể để mặc định USER thay vì ADMIN

        // Sinh OTP
        String otp = OtpUtil.generateOtp();
        newUser.setVerificationCode(otp);
        newUser.setVerificationExpiry(Instant.now().plusSeconds(300)); // hết hạn sau 5 phút

        User createdUser = userRepository.save(newUser);

        // Gửi mail
        mailService.sendMailAsync(createdUser.getEmail(), "Email Verification Code", "Your verification code is: " + otp);

        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(createdUser))
                .token(null) // Không trả về token khi đăng ký, yêu cầu xác thực OTP
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.WRONG_PASSWORD);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Tạo JWT token nhưng không lưu vào DB
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(user))
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse verifyOtp(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getVerificationExpiry().isBefore(Instant.now())) {
            throw new RuntimeException("OTP expired");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(user))
                .token(jwtToken)
                .build();
    }
}
