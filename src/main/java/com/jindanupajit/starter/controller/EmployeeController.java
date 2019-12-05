package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.EmployeeForm;
import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.DepartmentRepository;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.util.Verbose;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @PersistenceContext
    protected EntityManager entityManager;

    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {
        Verbose.printlnf("Init ModelAttribute for '%s' (%s)", request.getRequestURI(), request.getMethod());
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

        if (employee.getId() == 0) {
            Verbose.printlnf("Error: No such employee (id='" + idString + "')!");
            model.addAttribute("error", "No such employee (id='" + idString + "')!");
        }
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
            Verbose.printlnf("Error: 'Password' and 'Password Verify' must match!");
            model.addAttribute("error", "'Password' and 'Password Verify' must match!");
            return "employee";
        }

        if (employeeForm.getDepartment() == null) {
            Verbose.printlnf("Error: Must select a department!");
            model.addAttribute("error", "Must select a department!");
            return "employee";
        }

        Role roleUser = roleRepository.findByAuthority("USER");

        employee.setAuthorities(Collections.singletonList(roleUser));

        Verbose.printlnf("Save: Employee('%s')", employeeForm.getUsername());
        employeeRepository.save(employee);

        return "redirect:/employee/edit?success=Employee+saved%21&id="+employee.getId();
    }

    @PostMapping(value="/edit")
    public String editProcess(Model model, @ModelAttribute EmployeeForm employeeForm){

        System.out.println("EmployeeController::editProcess(): Data received: "+employeeForm);

        Employee alteredEmployee = employeeForm.toEmployee();

        Optional<Employee> optionalEmployee = employeeRepository.findById(alteredEmployee.getId());

        model.addAttribute("formObject", employeeForm);

        if (!optionalEmployee.isPresent()) {
            employeeForm.setId(0);
            model.addAttribute("action", ActionType.PERSIST);
            Verbose.printlnf("Error: No such employee (id='" + alteredEmployee.getId() + "')!");
            model.addAttribute("error", "No such employee (id='" + alteredEmployee.getId() + "')!");
            return "employee";
        }


        if ((!employeeForm.getPassword().equals(""))&&(!employeeForm.getPassword().equals(employeeForm.getPasswordVerify()))) {
            model.addAttribute("action", ActionType.MERGE);
            Verbose.printlnf("Error: 'Password' and 'Password Verify' must match!");
            model.addAttribute("error", "'Password' and 'Password Verify' must match!");
            return "employee";
        }

        if (employeeForm.getDepartment() == null) {
            model.addAttribute("action", ActionType.MERGE);
            Verbose.printlnf("Error: Must select a department!");
            model.addAttribute("error", "Must select a department!");
            return "employee";
        }

        Verbose.printlnf("Save: Employee('%s')", employeeForm.getUsername());
        employeeRepository.save(employeeForm.mergeTo(optionalEmployee.get()));

        return "redirect:/employee/edit?success=Employee+saved%21&id="+employeeForm.getId();
    }
}
