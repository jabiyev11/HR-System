package com.example.hr_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserRegisterDto {

    private String username;
    private String email;
    private String password;
}
