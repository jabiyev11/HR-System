package com.example.hr_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/dashboard")
    public String getInfoPage(){
        return "Viewing info page";
    }
}
