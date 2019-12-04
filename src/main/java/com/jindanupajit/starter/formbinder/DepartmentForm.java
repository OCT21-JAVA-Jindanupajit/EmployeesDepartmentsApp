package com.jindanupajit.starter.formbinder;


import com.jindanupajit.starter.model.Department;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Id;
@ActionMapping(Action= ActionType.PERSIST, Method= RequestMethod.POST, Url="/department/add", Label="Add")
@ActionMapping(Action=ActionType.MERGE, Method=RequestMethod.POST, Url="/department/edit", Label="Save")
@ActionMapping(Action=ActionType.DELETE, Method=RequestMethod.POST, Url="/department/delete", Label="Delete")
public class DepartmentForm {

    @Id
    private long id;

    @UserInput(Ordinal = 1, Label = "Department Name")
    private String name;

    public DepartmentForm() {
    }

    public DepartmentForm(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department toDepartment() {
        return new Department(this.id, this.name, null);
    }

    public static DepartmentForm fromDepartment(Department department) {
        return new DepartmentForm(department.getId(), department.getName());
    }
}
