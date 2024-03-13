package com.example.testing.repository;

import com.example.testing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // query with indexed parameters
    @Query("SELECT e from Employee e where e.firstName= ?1 and e.lastName= ?2")
    Employee findByName(String firstName, String lastName);

    @Query("SELECT e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findByNamedParams(@Param("firstName") String firstName,@Param("lastName") String lastName);
}
