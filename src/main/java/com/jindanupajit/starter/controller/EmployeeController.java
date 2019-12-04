package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.Credential;
import com.jindanupajit.starter.formbinder.EmployeeForm;
import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.DepartmentRepository;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.service.UserDetailsServiceImpl;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("departmentCollection", departmentRepository.findAllByOrderByName());
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("formObject", new EmployeeForm());
        model.addAttribute("action", ActionType.PERSIST);
        return "employee";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") String idString) {
        long id = Long.parseLong(idString);
        Employee employee = employeeRepository.findById(id).orElse(new Employee());

        if (employee.getId() == 0)
            model.addAttribute("error", "No such employee (id='"+idString+"')!");

        model.addAttribute("formObject", EmployeeForm.fromEmployee(employee));
        model.addAttribute("action", ActionType.MERGE);
        return "employee";
    }

    @PostMapping(value="/add")
    public String addProcess(Model model, @ModelAttribute EmployeeForm employeeForm){
        Employee employee = employeeForm.toEmployee();

        model.addAttribute("formObject", employeeForm);
        model.addAttribute("action", ActionType.PERSIST);

        if (!employeeForm.getPassword().equals(employeeForm.getPasswordVerify())) {
            model.addAttribute("error", "'Password' and 'Password Verify' must match!");
            return "employee";
        }

        if (employeeForm.getDepartment() == null) {
            model.addAttribute("error", "Must select a department!");
            return "employee";
        }

        Role roleUser = roleRepository.findByAuthority("USER");

        employee.setAuthorities(Collections.singletonList(roleUser));

        employeeRepository.save(employee);
        employee.getDepartment().getEmployeeCollection().add(employee);

        departmentRepository.save(employee.getDepartment());

        return "redirect:/employee/edit?success=Employee+saved%21&id="+employee.getId();
    }

    @PostMapping(value="/edit")
    public String editProcess(Model model, @ModelAttribute EmployeeForm employeeForm){
        Employee alteredEmployee = employeeForm.toEmployee();
        Employee employee = employeeRepository.findById(alteredEmployee.getId()).orElse(new Employee());

        model.addAttribute("formObject", employeeForm);

        if (employee.getId() == 0) {
            employeeForm.setId(0);
            model.addAttribute("action", ActionType.PERSIST);
            model.addAttribute("error", "No such employee (id='" + alteredEmployee.getId() + "')!");
            return "employee";
        }


        if ((!employeeForm.getPassword().equals(""))&&(!employeeForm.getPassword().equals(employeeForm.getPasswordVerify()))) {
            model.addAttribute("action", ActionType.MERGE);
            model.addAttribute("error", "'Password' and 'Password Verify' must match!");
            return "employee";
        } else {
            alteredEmployee.setPassword(employee.getPassword());
        }


        if (alteredEmployee.getDepartment() == null) {
            model.addAttribute("action", ActionType.MERGE);
            model.addAttribute("error", "Must select a department!");
            return "employee";
        }

        alteredEmployee.setAuthorities(employee.getAuthorities());
        employeeRepository.save(alteredEmployee);

        if (alteredEmployee.getDepartment() != employee.getDepartment()) {
            System.out.println("Department Changed!");
            employee.getDepartment().getEmployeeCollection().remove(employee);
            alteredEmployee.getDepartment().getEmployeeCollection().add(alteredEmployee);

            departmentRepository.saveAll(Arrays.asList(
                    employee.getDepartment(),
                    alteredEmployee.getDepartment()
            ));
        }


        return "redirect:/employee/edit?success=Employee+saved%21&id="+alteredEmployee.getId();
    }
}
