package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.RolesDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Employee;

import java.util.Collection;

public interface UserServices {
    EmployeeDTO saveUser(UsersDTO users);
    RolesDTO saveRole(Collection<RolesDTO> rolesDTOCollection);
    void addRoleToUser(String username, String role);
    EmployeeDTO getUserByUsername(String username);
    EmployeeDTO login(String username, String password);
}
