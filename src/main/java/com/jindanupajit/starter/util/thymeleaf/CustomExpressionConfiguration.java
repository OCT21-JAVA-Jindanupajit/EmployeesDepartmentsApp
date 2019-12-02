package com.jindanupajit.starter.util.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomExpressionConfiguration {


    @Bean
    public CustomExpressionObjectDialect customExpressionObjectDialect() {
        System.out.println("CustomExpressionObjectDialect() registered!");
        return new CustomExpressionObjectDialect();
    }
}
