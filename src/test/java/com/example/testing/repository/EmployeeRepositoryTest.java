package com.example.testing.repository;

import com.example.testing.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;


    // test save employee
    @Test
    @DisplayName("Test Save Employee")
    public void givenEmployeeObject_whenSave_thenReturnEmployee() {
        //given
        Employee employee = Employee
                .builder()
                .firstName("Benson")
                .lastName("Astrakhan")
                .email("e@h.v")
                .build();

        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test findAll Employees")
    public void givenEmployees_whenFindAll_thenReturnEmployeeList(){
        Employee employee = Employee
                .builder()
                .firstName("Benson")
                .lastName("Astrakhan")
                .email("e@h.v")
                .build();
        Employee employee1 = Employee
                .builder()
                .firstName("Alex")
                .lastName("Given")
                .email("geel@gnk.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }



     @Test
     @DisplayName("Find Employee By Id")
     public void givenEmployee_whenFindById_thenReturnEmployee(){
         //given - precondition / setup
         Employee employee = Employee
                 .builder()
                 .firstName("Benson")
                 .lastName("Astrakhan")
                 .email("e@h.v")
                 .build();
         employeeRepository.save(employee);

         //when
         Employee savedEmployee = employeeRepository.findById(employee.getId()).get();

         //then - verify the output
         Assertions.assertThat(savedEmployee).isNotNull();
     }


     @Test
     @DisplayName("Name to Display")
     public void givenEmployeeObject_whenFindByEmail_thenReturnEmployee(){
         //given - precondition / setup
         Employee employee = Employee
                 .builder()
                 .firstName("Benson")
                 .lastName("Astrakhan")
                 .email("e@h.v")
                 .build();
         employeeRepository.save(employee);

         //when - action or behaviour that we are testing
         Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

         //then - verify the output
         Assertions.assertThat(savedEmployee.get()).isNotNull();
     }


      @Test
      @DisplayName("Test Get Employee By Names")
      public void givenFirstNameAndLastName_whenFindByName_thenReturnEmployee(){
          //given - precondition / setup
          Employee employee = Employee
                  .builder()
                  .firstName("Benson")
                  .lastName("Astrakhan")
                  .email("e@h.v")
                  .build();
          employeeRepository.save(employee);
          var firstName = "Benson";
          var lastName = "Astrakhan";

          //when - action or behaviour that we are testing
          Employee employee1 = employeeRepository.findByName(firstName, lastName);

          //then - verify the output
          Assertions.assertThat(employee1).isNotNull();
      }


}
