package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.LoginForm;
import com.jindanupajit.starter.model.repository.DepartmentRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.util.Verbose;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {
        Verbose.printlnf("Init ModelAttribute for '%s'", request.getRequestURI());
        model.addAttribute("departmentCollection", departmentRepository.findAllByOrderByName());
    }

    @RequestMapping(value="/", method = {RequestMethod.GET, RequestMethod.POST})
    public String HomePage(Model model) {

        return "redirect:/department/search";
    }



    @GetMapping("/login")
    public String login(Model model, @ModelAttribute LoginForm loginForm) {
        model.addAttribute("formObject", loginForm);
        model.addAttribute("action", ActionType.LOGIN);
        return "login";
    }


}
