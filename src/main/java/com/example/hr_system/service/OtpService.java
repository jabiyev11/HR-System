package com.example.hr_system.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();


    public String generateOtp(String email){

        String otp = String.valueOf(new Random().nextInt(100000, 999999));
        otpStore.put(email, otp);
        System.out.println("OTP is: " + otp);
        return otp;
    }

    public boolean validateOtp(String email, String inputOtp){
        return otpStore.get(email) != null && otpStore.get(email).equals(inputOtp);
    }

    public void clearOtp(String email){
        otpStore.remove(email);
    }

}
