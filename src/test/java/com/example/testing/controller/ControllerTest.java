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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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


      @Test
      @DisplayName("Get All Employees test")
      public void givenEmployeeList_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception{
          //given - precondition / setup
          BDDMockito.given(employeeServce.getAllEmployees()).willReturn(List.of(employee, employee));

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
        BDDMockito.given(employeeServce.getEmployeeByID(anyLong())).willReturn(Optional.of(employee));
        //when - action or behaviour that we are testing
        ResultActions response = mockMvc.perform(get("/api/employees/1"));
        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.notNullValue()));
    }

    @Test
    public void givenInvalidId_whenGetEmployeeById_thenReturnNotFound() throws Exception{
        //given - precondition / setup
        BDDMockito.given(employeeServce.getEmployeeByID(anyLong())).willReturn(Optional.empty());
        //when - action or behaviour that we are testing
        ResultActions response = mockMvc.perform(get("/api/employees/1"));
        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
