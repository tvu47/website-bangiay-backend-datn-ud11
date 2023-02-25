package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserRepository userRepository;

    UserServices userServices;
    @GetMapping
    public ResponseEntity<List<Users>> getUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<Users> createUser(@RequestBody UsersDTO users){
//        return new ResponseEntity<>(userServices.saveUser(users), HttpStatus.OK);
//    }
}
