package com.jindanupajit.starter.initializer;

import com.jindanupajit.starter.model.*;
import com.jindanupajit.starter.model.repository.*;
import com.jindanupajit.starter.service.PasswordEncoder;
import com.jindanupajit.starter.util.Verbose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        Verbose.printlnf("Begin");


        Role roleAdmin, roleUser;

        if (roleRepository.count() == 0) {
            Verbose.printlnf("Begin - role");
            roleAdmin = new Role("ADMIN");
            roleUser = new Role("USER");

            Verbose.printlnf("Add Role('%s') and Role('%s')",roleAdmin.getAuthority(), roleUser.getAuthority());
            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        } else {
            Verbose.printlnf("Skipped - role");
            Verbose.printlnf("roleRepository.findByAuthority(\"ADMIN\")");
            roleAdmin = roleRepository.findByAuthority("ADMIN");
            Verbose.printlnf("roleRepository.findByAuthority(\"USER\")");
            roleUser = roleRepository.findByAuthority("USER");
            Verbose.printlnf("End of finding");
        }



        Department deptIT, deptRND;
        List<Department> allDepartment = new ArrayList<>();

        if (departmentRepository.count() == 0) {
            Verbose.printlnf("Begin - department");
            Arrays.asList(
                "Information Technology",
                "Production",
                "Research and Development (R&D)",
                "Purchasing",
                "Marketing",
                "Sales",
                "Human Resource Management",
                "Accounting and Finance").forEach(
                    (deptName) -> {
                        Department dept = new Department(deptName);
                        Verbose.printlnf("Add Department('%s')", dept);
                        departmentRepository.save(dept);
                        allDepartment.add(dept);
                    }
            );
        } else {
            Verbose.printlnf("Skipped - department");
        }

        deptIT = departmentRepository.findByName("Information Technology");
        deptRND = departmentRepository.findByName("Research and Development (R&D)");


        if (employeeRepository.count() == 0) {
            Verbose.printlnf("Begin - employee");
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
                    employeeJohnDoe.setDepartment(deptRND);

            Employee employeeJaneDoe = new Employee("Jane Doe",
                    "jane.doe@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));
                    employeeJaneDoe.setDepartment(deptRND);

            Employee employeeJackDoe = new Employee("Jack Doe",
                    "jack.doe@example.com",
                    PasswordEncoder.getInstance().encode("password"),
                    Collections.singletonList(roleUser));
                    employeeJackDoe.setDepartment(deptRND);

            Verbose.printlnf("Add Employee('%s'), Employee('%s'), Employee('%s'), Employee('%s'), Employee('%s')",
                    employeeAdmin, employeeKrissada,
                    employeeJohnDoe, employeeJaneDoe, employeeJackDoe);
            employeeRepository.saveAll(Arrays.asList(
                    employeeAdmin, employeeKrissada,
                    employeeJohnDoe, employeeJaneDoe, employeeJackDoe));

            String name = "Fay Havlik\n" +
                    "Brandie Pierpont\n" +
                    "Ernesto Moncrief\n" +
                    "Sharika Sheperd\n" +
                    "Tam Jeffress\n" +
                    "Hana Essex\n" +
                    "Ty Shank\n" +
                    "August Mcgivney\n" +
                    "Mabelle Schuster\n" +
                    "Remedios Yale\n" +
                    "Shad Chaparro\n" +
                    "Leeanna Ivy\n" +
                    "Julissa Rossman\n" +
                    "Keenan Raborn\n" +
                    "Mack Atwater\n" +
                    "Sharla Vanallen\n" +
                    "Tonya Buchholtz\n" +
                    "Marguerite Astin\n" +
                    "Reda Albertson\n" +
                    "Syble Goudy\n" +
                    "Berenice Clymer\n" +
                    "Felisha Timms\n" +
                    "Jennine Mackinnon\n" +
                    "Isela Rowlette\n" +
                    "Lurlene Gribble\n" +
                    "Gladis Gullett\n" +
                    "Mireille Ludlum\n" +
                    "Yesenia Kring\n" +
                    "Justina Porco\n" +
                    "Loyd Mccarver\n" +
                    "Bruna Kober\n" +
                    "Vesta Schlichting\n" +
                    "Chastity Jinkins\n" +
                    "Brigida Santoro\n" +
                    "Deandrea Lafreniere\n" +
                    "Milissa Beckerman\n" +
                    "Erik Cundiff\n" +
                    "Contessa Sheth\n" +
                    "Amy Lesesne\n" +
                    "Julianna Llamas\n" +
                    "Marlys Sieg\n" +
                    "Isabel Giardina\n" +
                    "Marcelina Pinkham\n" +
                    "Leatrice Kellerhouse\n" +
                    "Ruben Dunleavy\n" +
                    "Tonette Kleiber\n" +
                    "Lenora Bradham\n" +
                    "Elida Delaughter\n" +
                    "Denis Alves\n" +
                    "Morgan Lafontant";

            List<String> allDisplayName = Arrays.asList(name.split("\n"));
            Random r = new Random();
            allDisplayName.forEach( (displayName) -> {
                displayName = displayName.trim();

                Employee employee = new Employee(displayName.trim(),
                        displayName.trim().toLowerCase().replace(" ", ".")+"@example.com",
                        PasswordEncoder.getInstance().encode("password"),
                        Collections.singletonList(roleUser));
                employee.setDepartment(allDepartment.get(r.nextInt(allDepartment.size())));
                employeeRepository.save(employee);
            } );

        } else {
            Verbose.printlnf("Skipped - employee");
        }

        Verbose.printlnf("End");

    }
}
