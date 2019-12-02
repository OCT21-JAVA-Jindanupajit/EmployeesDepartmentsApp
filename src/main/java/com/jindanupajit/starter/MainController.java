package com.jindanupajit.starter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    private String HomePage() {
        return "view";
    }
}
