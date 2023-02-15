package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.RolesDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Users;

import java.util.Collection;

public interface UserServices {
    Users saveUser(UsersDTO users);
    RolesDTO saveRole(Collection<RolesDTO> rolesDTOCollection);
    void addRoleToUser(String username, String role);
    UsersDTO getUserByUsername(String username);
    EmployeeDTO employeeLogin(String username, String password);

}
