package com.example.testing.service;

import com.example.testing.entity.Employee;
import com.example.testing.exception.RecordExistsException;
import com.example.testing.repository.EmployeeRepository;
import com.example.testing.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup() {
        //        employeeRepository = Mockito.mock(EmployeeRepository.class);
        //        employeeServce = new EmployeeServiceImpl(employeeRepository);
        employee = Employee
                .builder()
                .id(1L)
                .firstName("Heloen")
                .lastName("Hill")
                .email("hill@k.v")
                .build();
    }


    @Test
    @DisplayName("Test Save Employee")
    public void givenEmployee_whenSaveEmployee_thenEmployee() {
        //given - precondition / setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behaviour that we are testing
        Employee employeeSaved = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(employeeSaved).isNotNull();
    }

    @Test
    @DisplayName("Test save Existing Employee throws an Error")
    public void givenExistingEmployee_whenSaveEmployee_thenThrowException(){
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        org.junit.jupiter.api.Assertions.assertThrows(RecordExistsException.class, ()->{
            employeeService.saveEmployee(employee);
        });

        // verify the save method is never called
        verify(employeeRepository, never()).save(any(Employee.class));
    }


}
