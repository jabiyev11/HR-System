package com.example.hr_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponse {

    private boolean success;
    private String message;

}
