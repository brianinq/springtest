package com.example.testing.controller;

import com.example.testing.entity.Employee;
import com.example.testing.service.EmployeeServce;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    public EmployeeServce employeeServce;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;
    @BeforeEach
    void setup(){
        employee = Employee
                .builder()
                .id(1L)
                .firstName("Relic")
                .lastName("Dent")
                .build();
    }


     @Test
     @DisplayName("Post /employees test")
     public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
         //given
         BDDMockito.given(employeeServce.saveEmployee(any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
         //when
         ResultActions response = mockMvc
                 .perform(post("/api/employees")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(employee)));
         //then
         response.andExpect(MockMvcResultMatchers.status().isCreated())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(employee.getId().intValue())));
     }


}
