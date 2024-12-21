package com.example.hr_system.service;


import com.example.hr_system.dto.EmployeeResponse;
import com.example.hr_system.entity.User;
import com.example.hr_system.repository.HrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrService {

    private final HrRepository hrRepository;

    public List<EmployeeResponse> listAllEmployees(){

        List<User> employees = hrRepository.getAllEmployees();

        return employees.stream()
                .map(this::covertToResponseDTO)
                .collect(Collectors.toList());
    }


    private EmployeeResponse covertToResponseDTO(User user){

        return new EmployeeResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDepartment(),
                user.getStatus()
        );
    }


}
