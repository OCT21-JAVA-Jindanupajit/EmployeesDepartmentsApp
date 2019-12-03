package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.model.Person;
import com.jindanupajit.starter.model.repository.PersonRepository;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/")
    private String HomePage(Model model) {

        model.addAttribute("person", new Person("John"));
        return "view";
    }

    @PostMapping(value="/person/process")

    private String personProcess(Model model, @ModelAttribute Person person){

        personRepository.save(person);

        model.addAttribute("person", person);
        return "view";
    }
}
