package com.example.mp5spring.repository;

import java.util.Optional;

import com.example.mp5spring.model.Department;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Query("from Department as d join fetch d.employees where d.id = :id")
    public Optional<Department> findById(@Param("id") Long id);
}
