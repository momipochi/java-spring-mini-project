package com.example.mp5spring.repository;

import com.example.mp5spring.model.Employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    
}
