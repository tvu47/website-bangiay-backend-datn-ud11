package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.services.auth.UserAuth;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    Users findUserByUsername(String user) throws UserNotfoundException;
    UserDetails getUserDetailFromDB(String username);
    Users createUsers(String username, String password);
    RoleUser createRoleUser(RoleUser roleUser);

    Users IsUserExist(String username) throws UserExistedException;

    List<RoleUser> IsRoleUserExist(String username) throws UserExistedException;
    Users updateUser(Users users);
}
