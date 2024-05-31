package com.example.mp5spring.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mp5spring.model.Employee;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee smartCopy(UserAccount subclass) {
        // TODO Auto-generated method stub
        if (subclass == null) {
            try {
                throw new Exception("Smart copy object should not be null");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new Employee(subclass);
    }

}
