package com.jindanupajit.starter.controller;

import com.jindanupajit.starter.formbinder.DepartmentForm;
import com.jindanupajit.starter.formbinder.EmployeeForm;
import com.jindanupajit.starter.model.Department;
import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.model.Role;
import com.jindanupajit.starter.model.repository.DepartmentRepository;
import com.jindanupajit.starter.model.repository.EmployeeRepository;
import com.jindanupajit.starter.model.repository.RoleRepository;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public void init(Model model) {

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

        if (department.getId() == 0)
            model.addAttribute("error", "No such department (id='"+idString+"')!");

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
            model.addAttribute("error", "Department must has name!");
            return "department";
        }

        departmentRepository.save(department);

        return "redirect:/department/edit?success=Department+saved%21&id="+department.getId();
    }

    @PostMapping(value="/edit")
    public String editProcess(Model model, @ModelAttribute DepartmentForm departmentForm){
        Department alteredDepartment = departmentForm.toDepartment();
        Department department = departmentRepository.findById(departmentForm.getId()).orElse(new Department());

        model.addAttribute("formObject", departmentForm);

        if (department.getId() == 0) {
            departmentForm.setId(0);
            model.addAttribute("action", ActionType.PERSIST);
            model.addAttribute("error", "No such department (id='" + alteredDepartment.getId() + "')!");
            return "department";
        }

        if (departmentForm.getName().trim().equals("")) {
            model.addAttribute("action", ActionType.MERGE);
            model.addAttribute("error", "Department must has name!");
            return "department";
        }

        alteredDepartment.setEmployeeCollection(department.getEmployeeCollection());

        departmentRepository.save(alteredDepartment);

        return "redirect:/department/edit?success=Department+saved%21&id="+alteredDepartment.getId();
    }
}
