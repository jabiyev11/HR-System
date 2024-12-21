package com.example.hr_system.repository;

import com.example.hr_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HrRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u
            FROM User u
            JOIN u.roles r
            WHERE r.id = 3""")
    List<User> getAllEmployees();
}
