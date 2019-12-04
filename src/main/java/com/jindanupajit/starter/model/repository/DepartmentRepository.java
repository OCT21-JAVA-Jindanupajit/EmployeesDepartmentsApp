package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository  extends CrudRepository<Department, Long> {
    List<Department> findAllByOrderByName();
    Department findByName(String name);
}
