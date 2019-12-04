package com.jindanupajit.starter.service;

import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.printf("%s: Trying to Authenticate '%s'\n", this.getClass().getName(), userName);
        Employee employee = employeeRepository.findByUsername(userName);

        if (employee == null)
            throw new UsernameNotFoundException("User '"+userName+"' not found.");

        return employee;
    }

    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

//    public static void initModelAttributes(Model model) {
//        // Depreciated, Do it hard-way (use thymeleaf-extras-springsecurity5 instead)
//        // Global attributes
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken)
//            authentication.setAuthenticated(false);
//        if (authentication.isAuthenticated()) {
//            Employee employee = employeeRepository.findByUsername(authentication.getName());
//            Role roleUser = roleRepository.findByAuthority("USER");
//            Role roleAdmin = roleRepository.findByAuthority("ADMIN");
//
//            model.addAttribute("authenticatedUser", employee);
//            model.addAttribute("isUser",authentication.getAuthorities().contains(roleUser));
//            model.addAttribute("isAdmin",authentication.getAuthorities().contains(roleAdmin));
//        }
//        model.addAttribute("authentication", authentication);
//    }
}
