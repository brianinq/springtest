package com.example.testing.service;

import com.example.testing.entity.Employee;
import com.example.testing.repository.EmployeeRepository;
import com.example.testing.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
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
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behaviour that we are testing
        Employee employeeSaved = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(employeeSaved).isNotNull();
    }


}
