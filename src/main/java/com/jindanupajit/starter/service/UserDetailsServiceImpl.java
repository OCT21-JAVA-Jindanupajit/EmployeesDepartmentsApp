package com.jindanupajit.starter.service;

import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.util.Verbose;
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
        Verbose.printlnf("Trying to Authenticate '%s'\n", userName);
        Employee employee = employeeRepository.findByUsername(userName);

        if (employee == null)
            throw new UsernameNotFoundException("User '"+userName+"' not found.");

        return employee;
    }

    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

}
