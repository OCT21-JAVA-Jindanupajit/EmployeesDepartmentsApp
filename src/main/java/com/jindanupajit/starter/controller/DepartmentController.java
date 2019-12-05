package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.DepartmentForm;
import com.jindanupajit.starter.formbinder.EmployeeForm;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {
        Verbose.printlnf("Init ModelAttribute for '%s'", request.getRequestURI());
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("formObject", new DepartmentForm());
        model.addAttribute("action", ActionType.PERSIST);
        return "department";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") String idString) {
        long id = Long.parseLong(idString);
        Department department = departmentRepository.findById(id).orElse(new Department());

        if (department.getId() == 0) {
            Verbose.printlnf("Error: No such department (id='" + idString + "')!");
            model.addAttribute("error", "No such department (id='" + idString + "')!");
        }
        model.addAttribute("formObject", DepartmentForm.fromDepartment(department));
        model.addAttribute("action", ActionType.MERGE);
        return "department";
    }

    @PostMapping(value="/add")
    public String addProcess(Model model, @ModelAttribute DepartmentForm departmentForm){
        Department department = departmentForm.toDepartment();

        model.addAttribute("formObject", departmentForm);
        model.addAttribute("action", ActionType.PERSIST);

        if (departmentForm.getName().trim().equals("")) {
            Verbose.printlnf("Error: Department must has name!");
            model.addAttribute("error", "Department must has name!");
            return "department";
        }

        Verbose.printlnf("Save: Department('%s')", departmentForm.getName());
        departmentRepository.save(department);

        return "redirect:/department/edit?success=Department+saved%21&id="+department.getId();
    }

    @PostMapping(value="/edit")
    public String editProcess(Model model, @ModelAttribute DepartmentForm departmentForm){

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentForm.getId());

        model.addAttribute("formObject", departmentForm);

        if (!optionalDepartment.isPresent()) {
            departmentForm.setId(0);
            model.addAttribute("action", ActionType.PERSIST);
            Verbose.printlnf("Error: No such department (id='" + departmentForm.getId() + "')!");
            model.addAttribute("error", "No such department (id='" + departmentForm.getId() + "')!");
            return "department";
        }

        departmentForm.setName(departmentForm.getName().trim());

        if (departmentForm.getName().equals("")) {
            model.addAttribute("action", ActionType.MERGE);
            Verbose.printlnf("Error: Department must has name!");
            model.addAttribute("error", "Department must has name!");
            return "department";
        }


        Verbose.printlnf("Save: Department('%s')", departmentForm.getName());
        departmentRepository.save(departmentForm.mergeTo(optionalDepartment.get()));

        return "redirect:/department/edit?success=Department+saved%21&id="+departmentForm.getId();
    }
}
