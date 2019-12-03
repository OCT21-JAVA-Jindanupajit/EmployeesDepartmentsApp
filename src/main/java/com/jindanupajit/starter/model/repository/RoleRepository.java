package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByAuthority(String authority);
}
