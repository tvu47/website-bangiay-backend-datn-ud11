package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Employee;
import com.snackman.datnud11.repo.EmployeeRepository;
import com.snackman.datnud11.services.EmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
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
    @Autowired
    EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployee(){
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee) throws CustomNotFoundException {
        employeeService.checkEmployeeExist(employee.getId());
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteEmployeeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        Employee employee = employeeService.checkEmployeeExist(id);
        employeeRepository.delete(employee);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
