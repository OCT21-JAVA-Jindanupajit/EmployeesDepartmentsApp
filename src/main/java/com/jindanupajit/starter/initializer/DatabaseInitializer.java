package com.jindanupajit.starter.initializer;

import com.jindanupajit.starter.model.*;
import com.jindanupajit.starter.model.repository.*;
import com.jindanupajit.starter.service.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public void run(String... args) {
        Role roleAdmin, roleUser;
        if (roleRepository.count() == 0) {
            roleAdmin = new Role("ADMIN");
            roleUser = new Role("USER");


            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        } else {
            roleAdmin = roleRepository.findByAuthority("ADMIN");
            roleUser = roleRepository.findByAuthority("USER");
        }

        if (userRepository.count() == 0) {
            User userAdmin = new User("Administrator",
                    "admin",
                    PasswordEncoder.getInstance().encode("password"),
                    Arrays.asList(roleAdmin, roleUser));
            User userUser1 = new User("User #1",
                    "user1",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));

            userRepository.saveAll(Arrays.asList(userAdmin, userUser1));

        }
    }
}
