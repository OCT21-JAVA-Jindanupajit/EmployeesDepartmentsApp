package com.jindanupajit.starter.service;

import com.jindanupajit.starter.model.User;
import com.jindanupajit.starter.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);

        if (user == null)
            throw new UsernameNotFoundException("User '"+userName+"' not found.");

        return user;
    }

    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }
}
