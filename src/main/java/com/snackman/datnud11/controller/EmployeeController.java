package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.request.EmployeeRequest;
import com.snackman.datnud11.entity.Employee;
import com.snackman.datnud11.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return new ResponseEntity<>(employeeService.addEmployee(employeeRequest), HttpStatus.CREATED);
    }
}
