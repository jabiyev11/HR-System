package com.example.hr_system.dto;

import com.example.hr_system.enums.Department;
import com.example.hr_system.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String username;
    private String email;
    private Department department;
    private Status status;
}
