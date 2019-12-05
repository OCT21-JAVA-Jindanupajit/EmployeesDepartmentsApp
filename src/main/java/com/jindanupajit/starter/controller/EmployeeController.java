package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.EmployeeForm;
import com.jindanupajit.starter.formbinder.EmployeeSearchForm;
import com.jindanupajit.starter.model.Department;
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

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name="q") Optional<String> query) {
        Verbose.printlnf("Search Employee for '%s'", query.orElse("all"));
        model.addAttribute("viewObject",
                query.isPresent()?
                    employeeRepository.findAllByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByDisplayName(query.get(), query.get()):
                    employeeRepository.findAllByOrderByDisplayName());
        model.addAttribute("searchObject", new EmployeeSearchForm());
        model.addAttribute("action", ActionType.VIEW);

        return "employee";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") String idString, @RequestParam("confirmed") boolean confirmed) {

        long id = Long.parseLong(idString);
        if (!confirmed)  return "redirect:/employee/edit?error=Deletion+not+confirmed%21&id="+id;

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (!optionalEmployee.isPresent()) return "redirect:/employee/search?error=Not+found%21";


        Employee employee = optionalEmployee.get();

        Department department = employee.getDepartment();
        if (department != null) {
            Verbose.printlnf("Unlink Employee('%s') from Department('%s')", employee.getUsername(), department.getName());

            department.getEmployeeCollection().remove(employee);
            departmentRepository.save(department);
        }

        if (employee.getAuthorities() != null) {
            for (Role eachRole : employee.getAuthorities()) {
                Verbose.printlnf("Unlink Employee('%s') from Role('%s')", employee.getUsername(), eachRole.getAuthority());
                eachRole.getEmployeeCollection().remove(employee);
                roleRepository.save(eachRole);
            }

            Verbose.printlnf("Unlink Department and Role from Employee(%s)", employee.getUsername());
            employee.getAuthorities().clear();
        }

        employee.setDepartment(null);
        employee.setAuthorities(null);
        employeeRepository.save(employee);

        Verbose.printlnf("Delete Employee('%s')", optionalEmployee.get().getUsername());
        employeeRepository.delete(employee);

        return "redirect:/employee/search?success=Employee+deleted.";
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
