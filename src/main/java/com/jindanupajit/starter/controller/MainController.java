package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.Credential;
import com.jindanupajit.starter.formbinder.EmployeeForm;
import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.DepartmentRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.service.UserDetailsServiceImpl;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class MainController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("departmentCollection", departmentRepository.findAllByOrderByName());
    }

    @GetMapping("/")
    public String HomePage(Model model) {

        return "view";
    }



    @GetMapping("/login")
    public String login(Model model, @ModelAttribute Credential credential) {
        model.addAttribute("formObject", credential);
        model.addAttribute("action", ActionType.LOGIN);
        return "login";
    }


}
