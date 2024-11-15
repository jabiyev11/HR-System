package com.example.hr_system.service;

import com.example.hr_system.config.CustomUserDetails;
import com.example.hr_system.dto.AuthRequest;
import com.example.hr_system.dto.UserRegisterDto;
import com.example.hr_system.entity.Role;
import com.example.hr_system.entity.User;
import com.example.hr_system.exception.UserAlreadyExistsException;
import com.example.hr_system.exception.WrongPasswordException;
import com.example.hr_system.repository.RoleRepository;
import com.example.hr_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public void registerUser(UserRegisterDto userRegisterDto) throws UserAlreadyExistsException {

        if(userRepository.existsByUsername(userRegisterDto.getUsername())){
            throw new UserAlreadyExistsException("Username is already taken");
        }

        Role defaultRole = roleRepository.findByName("USER")
                .orElseGet(() ->{
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        User user = new User();

        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRoles(Collections.singleton(defaultRole));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public String login(AuthRequest authRequest) throws WrongPasswordException {

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username, Try again"));

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new WrongPasswordException("Invalid password, Try again");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return jwtService.generateToken(userDetails);
    }
}
