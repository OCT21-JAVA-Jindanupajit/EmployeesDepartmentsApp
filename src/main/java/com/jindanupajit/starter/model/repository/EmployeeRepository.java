package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
}
