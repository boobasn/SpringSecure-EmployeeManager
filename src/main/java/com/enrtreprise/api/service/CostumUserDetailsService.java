package com.enrtreprise.api.service;

import com.enrtreprise.api.model.User;
import com.enrtreprise.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CostumUserDetailsService implements UserDetailsService {
 
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Important : préfixe "ROLE_" exigé par Spring
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                List.of(authority));
    }
}