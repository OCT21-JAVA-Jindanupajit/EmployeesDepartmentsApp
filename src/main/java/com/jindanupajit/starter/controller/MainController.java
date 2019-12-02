package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    private String HomePage(Model model) {

        model.addAttribute("person", new Person("John"));
        return "view";
    }
}
