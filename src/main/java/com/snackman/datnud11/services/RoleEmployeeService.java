package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.RoleEmployee;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface RoleEmployeeService {
    RoleEmployee checkRoleEmployeeExist(Long id) throws CustomNotFoundException;
}
