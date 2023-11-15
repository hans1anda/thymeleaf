package com.example.backend_app.service;

import com.example.backend_app.model.Employee;
import com.example.backend_app.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = null;
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        } else {
            throw new RuntimeException("Employee not found for id :: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);

    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,String keyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        System.out.println(keyword);
        if(Objects.isNull(keyword) || keyword.isEmpty()) {
            return employeeRepository.findAll(pageable);
        } else {
            return employeeRepository.findEmployeeByFirstName(keyword,pageable);
        }
    }

    @Override
    public List<Employee> findAllByName(String name) {
        return employeeRepository.findAllByFirstName(name);
    }


}
