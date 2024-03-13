package com.example.testing.service;

import com.example.testing.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServce {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeByID(Long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);
}
