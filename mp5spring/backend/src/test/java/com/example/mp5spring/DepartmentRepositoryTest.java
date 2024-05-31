package com.example.mp5spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.mp5spring.model.Department;
import com.example.mp5spring.repository.DepartmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Department d1;

    @BeforeEach
    public void initData() {
        d1 = Department.builder()
                .departmentName("departmentName")
                .build();
    }

    // standard check for dependencies
    @Test
    public void testRequiredDependencies() {
        assertNotNull(departmentRepository);
    }

    // testing if department was saved
    @Test
    public void testSaveDepartment() {
        departmentRepository.save(d1);
        long count = departmentRepository.count();
        assertEquals(1, count);
    }
}
