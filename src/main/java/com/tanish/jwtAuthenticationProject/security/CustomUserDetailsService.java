package com.tanish.jwtAuthenticationProject.security;

import com.tanish.jwtAuthenticationProject.entity.User;
import com.tanish.jwtAuthenticationProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User/ Email Not found"));
        return AuthUser.builder().user(user).build();
    }
}

