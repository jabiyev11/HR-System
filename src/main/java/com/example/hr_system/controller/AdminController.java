package com.example.hr_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/hr")
public class AdminController {


    @GetMapping("/dashboard")
    public String getAdminDashboard(){
        return "dashboard";
    }




}
