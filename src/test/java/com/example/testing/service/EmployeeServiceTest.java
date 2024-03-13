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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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


     @Test
     @DisplayName("Get All Employee")
     public void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){
         //given - precondition / setup
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee));
         //when - action or behaviour that we are testing
         List<Employee> employees = employeeService.getAllEmployees();

         //then - verify the output
         Assertions.assertThat(employees).isNotNull();
         Assertions.assertThat(employees.size()).isEqualTo(2);
     }

    @Test
    @DisplayName("Get All Employee on Empty")
    public void givenEmptyEmployeeList_whenFindAll_thenReturnEmptyEmployeeList(){
        //given - precondition / setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when - action or behaviour that we are testing
        List<Employee> employees = employeeService.getAllEmployees();

        //then - verify the output
        Assertions.assertThat(employees).isEmpty();
    }


     @Test
     @DisplayName("Find Employee By ID")
     public void givenEmployee_whenGetByID_thenReturnEmployee(){
         //given - precondition / setup
         given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

         //when - action or behaviour that we are testing
         Employee savedEmployee = employeeService.getEmployeeByID(1L).get();

         //then - verify the output
         Assertions.assertThat(savedEmployee).isNotNull();
     }


      @Test
      @DisplayName("Test Update Employee")
      public void givenEmployee_whenUpdateEmployee_thenReturnEmployee(){
          //given - precondition / setup
          given(employeeRepository.save(any(Employee.class))).willReturn(employee);
          employee.setEmail("e.g.c");
          employee.setFirstName("Going");

          //when - action or behaviour that we are testing
          Employee employeeUpdated = employeeService.updateEmployee(employee);

          //then - verify the output
          Assertions.assertThat(employeeUpdated).isNotNull();
          Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("e.g.c");
          Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Going");
      }


       @Test
       @DisplayName("Test Delete")
       public void givenID_whenDelete_thenReturnVoid(){
           //given - precondition / setup
           willDoNothing().given(employeeRepository).deleteById(anyLong());

           //when - action or behaviour that we are testing
           employeeService.deleteEmployeeById(employee.getId());

           //then - verify the output
           verify(employeeRepository, times(1)).deleteById(anyLong());
       }


}
