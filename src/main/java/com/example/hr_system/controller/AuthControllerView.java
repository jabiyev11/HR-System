package com.example.hr_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerView {


    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String getSignupPage(){
        return "signUp";
    }

    @GetMapping("/verify-otp")
    public String getOtpPage(){
        return "otpVerification";
    }



}
