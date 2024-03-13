package com.example.testing.service.impl;

import com.example.testing.entity.Employee;
import com.example.testing.exception.RecordExistsException;
import com.example.testing.repository.EmployeeRepository;
import com.example.testing.service.EmployeeServce;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeServce {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent())
            throw new RecordExistsException("The email provided is already associated with another employee");

        return employeeRepository.save(employee);
    }
}
