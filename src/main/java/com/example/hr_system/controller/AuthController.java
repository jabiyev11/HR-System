package com.example.hr_system.controller;


import com.example.hr_system.dto.*;
import com.example.hr_system.exception.InvalidOtpException;
import com.example.hr_system.exception.UserAlreadyExistsException;
import com.example.hr_system.service.AuthService;
import com.example.hr_system.service.OtpService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpServletResponse http) throws Exception{
        return authService.login(authRequest);
//        return "redirect:/dashboard";
    }


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) throws Exception{
       return authService.registerUser(userRegisterRequest);
    }


    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public OtpVerificationResponse verifyOtp(@RequestParam String email, @RequestParam String otp) throws Exception {
        return authService.verifyOtp(email, otp);
    }
}
