package com.example.hr_system.repository;

import com.example.hr_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
        SELECT u.*
        FROM app_user u
        LEFT JOIN user_roles ur ON u.id = ur.user_id
        LEFT JOIN role r ON r.id = ur.role_id
        where u.username = :username
""", nativeQuery = true)
    Optional<User> findUsernameWithRoles(@Param("username") String username);
    Boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
