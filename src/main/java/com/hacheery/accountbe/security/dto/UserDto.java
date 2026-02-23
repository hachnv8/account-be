package com.hacheery.accountbe.security.dto;

import com.hacheery.accountbe.security.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}