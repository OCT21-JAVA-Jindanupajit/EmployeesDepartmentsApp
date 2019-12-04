package com.jindanupajit.starter.initializer;

import com.jindanupajit.starter.model.*;
import com.jindanupajit.starter.model.repository.*;
import com.jindanupajit.starter.service.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DatabaseInitializer implements CommandLineRunner {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    @Transactional
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

        Department deptIT, deptIntern;
        if (departmentRepository.count() == 0) {
            deptIT = new Department("Information Technology (IT)");
            deptIntern = new Department( "Internship");

            departmentRepository.saveAll(Arrays.asList(deptIT, deptIntern));
        } else {
            deptIT = departmentRepository.findByName("Information Technology (IT)");
            deptIntern = departmentRepository.findByName("Internship");
        }

        if (employeeRepository.count() == 0) {
            Employee employeeAdmin = new Employee("Administrator",
                    "admin@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Arrays.asList(roleAdmin, roleUser));
                    employeeAdmin.setDepartment(deptIT);
            Employee employeeKrissada = new Employee("Krissada",
                    "krissada@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Arrays.asList(roleAdmin, roleUser));
                    employeeKrissada.setDepartment(deptIT);
            Employee employeeJohnDoe = new Employee("John Doe",
                    "john.doe@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));
                    employeeJohnDoe.setDepartment(deptIntern);
            Employee employeeJaneDoe = new Employee("Jane Doe",
                    "jane.doe@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));
                    employeeJaneDoe.setDepartment(deptIntern);
            Employee employeeJackDoe = new Employee("Jack Doe",
                    "jack.doe@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));
                    employeeJackDoe.setDepartment(deptIntern);
            employeeRepository.saveAll(Arrays.asList(employeeAdmin, employeeKrissada, employeeJohnDoe, employeeJaneDoe, employeeJackDoe));

            deptIT.getEmployeeCollection().addAll(Arrays.asList(employeeAdmin, employeeKrissada));
            deptIntern.getEmployeeCollection().addAll(Arrays.asList(employeeJohnDoe, employeeJaneDoe, employeeJackDoe));

            departmentRepository.saveAll(Arrays.asList(deptIT, deptIntern));

        }



    }
}
