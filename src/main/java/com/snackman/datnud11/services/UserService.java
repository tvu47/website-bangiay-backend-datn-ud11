package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    Users findUserByUsername(String user) throws UserNotfoundException;
    UserDetails getUserDetailFromDB(String username) throws UserNotfoundException, RoleNotFoundException;
}
