package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.repo.RoleUserRepository;
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
    RoleUserRepository roleUserRepository;
    @GetMapping
    public ResponseEntity<List<RoleUser>> getRolesEmployee(){
        return new ResponseEntity<>(roleUserRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<RoleUser> createRolesEmployee(@RequestBody RoleUser roleUser){
        return new ResponseEntity<>(roleUserRepository.save(roleUser), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<RoleUser> updateRolesEmployeeById(@RequestBody RoleUser roleUser) throws CustomNotFoundException {
        roleEmployeeService.checkRoleEmployeeExist(roleUser.getEmployeeId());
        return new ResponseEntity<>(roleUser, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRolesEmployeeById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        RoleUser roleUser =  roleEmployeeService.checkRoleEmployeeExist(id);
        roleUserRepository.delete(roleUser);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
