package com.example.hr_system.controller;


import com.example.hr_system.dto.AuthRequest;
import com.example.hr_system.dto.AuthResponse;
import com.example.hr_system.dto.UserRegisterRequest;
import com.example.hr_system.exception.InvalidOtpException;
import com.example.hr_system.service.AuthService;
import com.example.hr_system.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception{
        String token = authService.login(authRequest);
        return new AuthResponse(token);
    }


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) throws Exception{
        authService.registerUser(userRegisterRequest);
        return "User registered successfully. Please check your email for OTP";
    }


    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) throws Exception {
        authService.verifyOtp(email, otp);
        return "Your email verified successfully";
    }
}
