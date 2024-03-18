package com.example.testing.integration;

import com.example.testing.entity.Employee;
import com.example.testing.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Post /employees test")
    public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        //given
        Employee employee = Employee
                .builder()
                .firstName("Relic")
                .lastName("Dent")
                .build();
        //when
        ResultActions response = mockMvc
                .perform(post("/api/employees")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(employee)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Get All Employees test")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception{
        //given - precondition / setup
        Employee employee = Employee
                .builder()
                .firstName("Relic")
                .lastName("Dent")
                .build();
        Employee employee1 = Employee
                .builder()
                .firstName("Relic")
                .lastName("Dent")
                .build();
        List<Employee> employees = List.of(employee1, employee);
        employeeRepository.saveAll(employees);

        //when - action or behaviour that we are testing
        ResultActions response = mockMvc.perform(
                get("/api/employees")
        );

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(2)));
    }

    @Test
    public void givenEmployee_whenGetEmployeeById_thenReturnEmployee() throws Exception{
        //given - precondition / setup
        Employee employee = Employee
                .builder()
                .firstName("Relic")
                .lastName("Dent")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour that we are testing
        ResultActions response = mockMvc.perform(get("/api/employees/"+employee.getId()));
        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.notNullValue()));
    }

    @Test
    public void givenInvalidId_whenGetEmployeeById_thenReturnNotFound() throws Exception{
        //when - action or behaviour that we are testing
        ResultActions response = mockMvc.perform(get("/api/employees/1"));
        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
