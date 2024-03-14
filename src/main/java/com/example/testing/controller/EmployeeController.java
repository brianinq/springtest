package com.example.testing.controller;

import com.example.testing.entity.Employee;
import com.example.testing.service.EmployeeServce;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeServce employeeServce;

    public EmployeeController(EmployeeServce employeeServce) {
        this.employeeServce = employeeServce;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeServce.saveEmployee(employee);
    }
}
