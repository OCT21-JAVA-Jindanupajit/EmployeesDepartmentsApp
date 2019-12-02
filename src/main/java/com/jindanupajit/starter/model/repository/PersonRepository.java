package com.jindanupajit.starter.model.repository;

import com.jindanupajit.starter.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
