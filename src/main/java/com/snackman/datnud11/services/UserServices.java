package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.RolesDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface UserServices {
    UserDetails getUserDetailFromDB(String username);
    List<String> getRoleListByUsername(String username);
}
