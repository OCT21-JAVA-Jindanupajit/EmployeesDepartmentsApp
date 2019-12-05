package com.jindanupajit.starter.formbinder;

import com.jindanupajit.starter.model.Department;
import com.jindanupajit.starter.model.Employee;
import com.jindanupajit.starter.service.PasswordEncoder;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Id;

@ActionMapping(Action=ActionType.PERSIST, Method=RequestMethod.POST, Url="/employee/add", Label="Add")
@ActionMapping(Action=ActionType.MERGE, Method=RequestMethod.POST, Url="/employee/edit", Label="Save")
@ActionMapping(Action=ActionType.DELETE, Method=RequestMethod.POST, Url="/employee/delete", Label="Delete")
public class EmployeeForm {

    @Id
    private long id;

    @UserInput(Ordinal = 1, Label = "Employee Name")
    private String displayName;

    @UserInput(Ordinal = 2, Label = "Email")
    private String username;

    @UserInput(Ordinal = 3, Label = "Password", Secret=true)
    private String password;

    @UserInput(Ordinal = 4, Label = "Password Verify", Secret=true)
    private String passwordVerify;

    @UserInput(Ordinal = 5, Label = "Department")
    private Department department;

    public EmployeeForm() {
    }

    public EmployeeForm(long id, String displayName, String username, String password, String passwordVerify, Department department) {
        this.id = id;
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.passwordVerify = passwordVerify;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerify() {
        return passwordVerify;
    }

    public void setPasswordVerify(String passwordVerify) {
        this.passwordVerify = passwordVerify;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee toEmployee() {
        Employee employee = new Employee(
                this.id,
                this.displayName,
                this.username,
                PasswordEncoder.getInstance().encode(this.password),
                null,
                this.department
                );
        return employee;
    }

    public static EmployeeForm fromEmployee(Employee employee) {
        EmployeeForm employeeForm = new EmployeeForm(
                employee.getId(),
                employee.getDisplayName(),
                employee.getUsername(),
                "","",
                employee.getDepartment());

        return employeeForm;
    }

    public Employee mergeTo(Employee employee) {
        employee.setDisplayName(this.displayName);
        employee.setUsername(this.username);
        if (!this.password.equals(""))
            employee.setPassword(PasswordEncoder.getInstance().encode(this.password));
        employee.setDepartment(this.department);
        return employee;
    }

    @Override
    public String toString() {
        return "EmployeeForm{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordVerify='" + passwordVerify + '\'' +
                ", department=" + department +
                '}';
    }
}
