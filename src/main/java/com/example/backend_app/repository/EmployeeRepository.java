package com.example.backend_app.repository;

import com.example.backend_app.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
public interface EmployeeRepository extends JpaRepository<Employee,Long>, ListCrudRepository<Employee, Long> {
    List<Employee> findAllByFirstName(String firstName);

    Page<Employee> findEmployeeByFirstName(String firstName, Pageable pageable);

    /*

    List<Employee> findAllByLastName(String lastName);
    Page<Employee> findEmployeeByLastName(String lastName, Pageable pageable);
    Employee findEmployeeByEmail(String email,Pageable pageable);

    */
}
