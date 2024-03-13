package com.example.testing.service;

import com.example.testing.entity.Employee;
import com.example.testing.exception.ResourceNotFoundException;
import com.example.testing.repository.EmployeeRepository;
import com.example.testing.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.awaitility.Awaitility.given;

public class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;
    private EmployeeServce employeeServce;

    @BeforeEach
    public void setup(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeServce = new EmployeeServiceImpl(employeeRepository);
    }


     @Test
     @DisplayName("Test Save Employee")
     public void givenEmployee_whenSaveEmployee_thenEmployee(){
         //given - precondition / setup
         Employee employee = Employee
                 .builder()
                 .id(1L)
                 .firstName("Heloen")
                 .lastName("Hill")
                 .email("hill@k.v")
                 .build();
         BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
         BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

         //when - action or behaviour that we are testing
         Employee employeeSaved = employeeServce.saveEmployee(employee);

         //then - verify the output
         Assertions.assertThat(employeeSaved).isNotNull();
     }





}
