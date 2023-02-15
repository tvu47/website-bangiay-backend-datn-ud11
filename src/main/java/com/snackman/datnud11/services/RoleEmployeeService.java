package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface RoleEmployeeService {
    RoleUser checkRoleEmployeeExist(Long id) throws CustomNotFoundException;
}
