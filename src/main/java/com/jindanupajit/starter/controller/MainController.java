package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.Credential;
import com.jindanupajit.starter.formbinder.UserRegistration;
import com.jindanupajit.starter.model.Person;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.User;
import com.jindanupajit.starter.model.repository.PersonRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.model.repository.UserRepository;
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
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @ModelAttribute
    public void principal(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            authentication.setAuthenticated(false);
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(authentication.getName());
            model.addAttribute("authenticatedUser", user);
        }
        model.addAttribute("authentication", authentication);
    }

    @GetMapping("/")
    public String HomePage(Model model) {

        model.addAttribute("formObject", new Person("John"));
        return "view";
    }

    @PostMapping(value="/person/process")
    public String personProcess(Model model, @ModelAttribute Person person){

        personRepository.save(person);

        model.addAttribute("person", person);
        return "view";
    }

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute Credential credential) {
        model.addAttribute("formObject", credential);
        model.addAttribute("action", ActionType.LOGIN);
        return "login";
    }

    @GetMapping("/user/add")
    public String userAdd(Model model) {
        model.addAttribute("formObject", new UserRegistration());
        model.addAttribute("action", ActionType.PERSIST);
        return "view";
    }

    @PostMapping(value="/user/add")
    public String personProcess(Model model, @ModelAttribute UserRegistration userRegistration){
        User user = userRegistration.toUser();
        if (userRegistration.getPassword().equals(userRegistration.getPasswordVerify()))
        userRepository.save(user);

        Role roleUser = roleRepository.findByAuthority("USER");

        user.setAuthorities(Collections.singletonList(roleUser));

        Credential credential = new Credential(user.getUsername(), "");
        model.addAttribute("formObject", credential);
        model.addAttribute("action", ActionType.LOGIN);
        return "view";
    }
}
