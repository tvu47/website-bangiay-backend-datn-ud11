package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.request.EmployeeRequest;
import com.snackman.datnud11.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(EmployeeRequest employee);
    List<Employee> getEmployees();
    Employee updateEmployee(Employee employee);
    boolean deleteEmployee(Long id);
}
