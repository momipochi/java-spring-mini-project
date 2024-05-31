package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
