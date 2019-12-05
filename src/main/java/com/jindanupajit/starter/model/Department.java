package com.jindanupajit.starter.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany (
            fetch = FetchType.LAZY,
            mappedBy = "department"
            // Reverse end
    )
    @OrderBy("displayName")
    private List<Employee> employeeCollection;

    public Department() {
        this.name = "";
        this.employeeCollection = new ArrayList<>();
    }

    public Department(String name) {
        this.name = name;
        this.employeeCollection = new ArrayList<>();
    }

    public Department(long id, String name, List<Employee> employeeCollection) {
        this.id = id;
        this.name = name;
        this.employeeCollection = employeeCollection;
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

    public List<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(List<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                getEmployeeCollection().equals(that.getEmployeeCollection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmployeeCollection());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
