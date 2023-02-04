package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.RoleEmployee;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-employee")
public class RoleEmployeeController {
    @Autowired
    RoleEmployeeService roleEmployeeService;
    @Autowired
    RoleEmployeeRepository roleEmployeeRepository;
    @GetMapping
    public ResponseEntity<List<RoleEmployee>> getRolesEmployee(){
        return new ResponseEntity<>(roleEmployeeRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<RoleEmployee> createRolesEmployee(@RequestBody RoleEmployee roleEmployee){
        return new ResponseEntity<>(roleEmployeeRepository.save(roleEmployee), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<RoleEmployee> updateRolesEmployeeById(@RequestBody RoleEmployee roleEmployee) throws CustomNotFoundException {
        roleEmployeeService.checkRoleEmployeeExist(roleEmployee.getEmployeeId());
        return new ResponseEntity<>(roleEmployee, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRolesEmployeeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        RoleEmployee roleEmployee =  roleEmployeeService.checkRoleEmployeeExist(id);
        roleEmployeeRepository.delete(roleEmployee);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
