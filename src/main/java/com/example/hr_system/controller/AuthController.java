package com.example.hr_system.controller;


import com.example.hr_system.dto.AuthRequest;
import com.example.hr_system.dto.AuthResponse;
import com.example.hr_system.dto.UserRegisterRequest;
import com.example.hr_system.dto.UserRegisterResponse;
import com.example.hr_system.exception.InvalidOtpException;
import com.example.hr_system.exception.UserAlreadyExistsException;
import com.example.hr_system.service.AuthService;
import com.example.hr_system.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
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


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) throws Exception{
        try {
            authService.registerUser(userRegisterRequest);
            return new UserRegisterResponse(true, "Signup successful! OTP sent to your email");
        } catch (UserAlreadyExistsException e) {
            return new UserRegisterResponse(false, "Problem happened during sign up!");
        }

    }


    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) throws Exception {
        authService.verifyOtp(email, otp);
        return "Your email verified successfully";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String getSignupPage(){
        return "signUp";
    }

    @GetMapping("/otpVerification")
    public String getOtpPage(){
        return "otpVerification";
    }

}
