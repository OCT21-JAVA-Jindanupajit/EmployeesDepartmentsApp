package com.jindanupajit.starter.initializer;

import com.jindanupajit.starter.model.*;
import com.jindanupajit.starter.model.repository.BookRepository;
import com.jindanupajit.starter.model.repository.PersonRepository;
import com.jindanupajit.starter.model.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public void run(String... args) {





    }
}
