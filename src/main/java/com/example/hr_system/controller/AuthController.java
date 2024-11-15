package com.example.hr_system.controller;


import com.example.hr_system.dto.AuthRequest;
import com.example.hr_system.dto.AuthResponse;
import com.example.hr_system.dto.UserRegisterDto;
import com.example.hr_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception{

        String token = authService.login(authRequest);
        return new AuthResponse(token);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserRegisterDto userRegisterDto) throws Exception{
        authService.registerUser(userRegisterDto);
        return "User registered successfully";
    }
}
