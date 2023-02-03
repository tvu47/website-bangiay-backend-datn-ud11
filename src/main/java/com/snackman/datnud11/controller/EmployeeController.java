package com.snackman.datnud11.controller;

import com.snackman.employeeservice.entity.Employee;
import com.snackman.employeeservice.repo.EmployeeRepository;
import com.snackman.employeeservice.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployee(){
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable(name = "id") Long id){
        Employee customers = new GenericObjFindById<Employee>().findByIdObject(employeeRepository.findById(id));
        return new ResponseEntity<>(customers, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteEmployeeById(@PathVariable(name = "id") Long id){
        Employee customers = new GenericObjFindById<Employee>().findByIdObject(employeeRepository.findById(id));
        employeeRepository.delete(customers);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
