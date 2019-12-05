package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
    List<Employee> findAllByOrderByDisplayName();
    List<Employee> findAllByDisplayNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrderByDisplayName(String displayName, String username);
}
