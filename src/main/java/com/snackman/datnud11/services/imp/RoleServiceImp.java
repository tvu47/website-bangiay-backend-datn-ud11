package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Roles;
import com.snackman.datnud11.repo.RolesRepository;
import com.snackman.datnud11.services.RoleService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RolesRepository rolesRepository;
    @Override
    public Roles checkRoleExist(Long id) throws CustomNotFoundException {
        Optional<Roles> optionalRoles = rolesRepository.findById(id);
        if (optionalRoles.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return optionalRoles.get();
    }
}
