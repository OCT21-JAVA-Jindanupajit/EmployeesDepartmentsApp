package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
