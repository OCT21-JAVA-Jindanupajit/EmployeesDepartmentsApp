package com.jindanupajit.starter.model;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NaturalId
    private String authority;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "authorities"
            // Reverse end
    )
    private Collection<Employee> employeeCollection;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public String toString() {
        return this.authority;
    }
}
