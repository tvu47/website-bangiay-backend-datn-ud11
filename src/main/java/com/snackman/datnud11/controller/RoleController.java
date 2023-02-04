package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Roles;
import com.snackman.datnud11.repo.RolesRepository;
import com.snackman.datnud11.services.RoleService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
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
    @Autowired
    RoleService roleService;
    @GetMapping
    public ResponseEntity<List<Roles>> getRoles(){
        return new ResponseEntity<>(rolesRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Roles> createRoles(@RequestBody Roles roles){
        return new ResponseEntity<>(rolesRepository.save(roles), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Roles> updateRolesById(@RequestBody Roles roles) throws CustomNotFoundException {
        roleService.checkRoleExist(roles.getId());
        return new ResponseEntity<>(roles, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteRolesById(@PathVariable(name = "id") Long id) throws CustomNotFoundException{
        Roles roles = roleService.checkRoleExist(id);
        rolesRepository.delete(roles);
        return new ResponseEntity<>("Delete Successfully!",HttpStatus.NO_CONTENT);
    }
}
