package com.snackman.datnud11.controller;

import com.snackman.employeeservice.entity.Roles;
import com.snackman.employeeservice.repo.RolesRepository;
import com.snackman.employeeservice.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    RolesRepository rolesRepository;

    @GetMapping
    public ResponseEntity<List<Roles>> getRoles(){
        return new ResponseEntity<>(rolesRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Roles> createRoles(@RequestBody Roles roles){
        return new ResponseEntity<>(rolesRepository.save(roles), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRolesById(@PathVariable(name = "id") Long id){
        Roles customers = new GenericObjFindById<Roles>().findByIdObject(rolesRepository.findById(id));
        return new ResponseEntity<>(customers, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteRolesById(@PathVariable(name = "id") Long id){
        Roles customers = new GenericObjFindById<Roles>().findByIdObject(rolesRepository.findById(id));
        rolesRepository.delete(customers);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
