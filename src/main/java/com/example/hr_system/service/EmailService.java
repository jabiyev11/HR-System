package com.example.hr_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendOtpEmail(String to, String otp){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        javaMailSender.send(message);
    }


}
