package com.andriikravchenkoo.carsaleproject.security.service;

import com.andriikravchenkoo.carsaleproject.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email = " + email + " not found"));
    }
}
