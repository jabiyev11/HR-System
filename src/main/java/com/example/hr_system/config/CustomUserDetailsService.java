package com.example.hr_system.config;

import com.example.hr_system.entity.User;
import com.example.hr_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        return new CustomUserDetails(user);
//
//        String roles = user.getRoles().stream()
//                .map(Role::getName)
//                .reduce((a, b) -> a + "," + b)
//                .orElse("");
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
    }
}
