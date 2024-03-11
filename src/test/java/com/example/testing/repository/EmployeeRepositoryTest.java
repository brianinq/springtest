package com.example.testing.repository;

import com.example.testing.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    @DisplayName("Test find Employee by Id")
    public void givenEmployee_whenFindByID_thenReturnEmployee(){

    }

    @Test
    @DisplayName("Name to Display")
    public void given_when_then(){
        //given - precondition / setup

        //when - action or behaviour that we are testing

        //then - verify the output
    }
}
