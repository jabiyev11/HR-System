package com.example.hr_system.service;

import com.example.hr_system.config.CustomUserDetails;
import com.example.hr_system.dto.*;
import com.example.hr_system.entity.Role;
import com.example.hr_system.entity.User;
import com.example.hr_system.exception.EmailRelatedException;
import com.example.hr_system.exception.UserAlreadyExistsException;
import com.example.hr_system.exception.UserNotVerifiedException;
import com.example.hr_system.exception.WrongPasswordException;
import com.example.hr_system.repository.RoleRepository;
import com.example.hr_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final EmailService emailService;


    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws UserAlreadyExistsException {

        if(userRepository.existsByUsername(userRegisterRequest.getUsername())){
            throw new UserAlreadyExistsException("Username is already taken");
        }

        Role defaultRole = roleRepository.findByName("USER")
                .orElseGet(() ->{
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        try {
            User user = new User();

            user.setEmail(userRegisterRequest.getEmail());
            user.setUsername(userRegisterRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setRoles(Collections.singleton(defaultRole));
            user.setCreatedAt(LocalDateTime.now());

            defaultRole.getUsers().add(user);

            String otp = otpService.generateOtp(userRegisterRequest.getEmail());
            emailService.sendOtpEmail(userRegisterRequest.getEmail(), otp);

            userRepository.save(user);
            return new UserRegisterResponse(true, "Signup successful! OTP sent to your email");

        } catch (Exception e) {
            return new UserRegisterResponse(false, "Problem happened during sign up!");
        }
    }

    public AuthResponse login(AuthRequest authRequest) throws Exception {

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username, Try again"));

//        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
//            throw new WrongPasswordException("Invalid password, Try again");
//        }

        if(!user.isVerified()){
            throw new UserNotVerifiedException("Check your email for OTP verification");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetails);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Set<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(accessToken, roles);
    }

    public OtpVerificationResponse verifyOtp(String email, String otp) throws EmailRelatedException {

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EmailRelatedException("Invalid email address"));

            if(user.isVerified()){
                throw new EmailRelatedException("User is already verified");
            }

            if(!otpService.validateOtp(email, otp)){
                throw new EmailRelatedException("Invalid or expired OTP");
            }

            user.setVerified(true);
            userRepository.save(user);

            return new OtpVerificationResponse(true, "OTP verified successfully");
        } catch (EmailRelatedException e) {
            return new OtpVerificationResponse(false, "OTP mismatch");
        }
    }
}
