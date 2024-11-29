package com.example.hr_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private Set<String> role;
}
