package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Roles;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface RoleService {
    Roles checkRoleExist(Long id) throws CustomNotFoundException;
}
