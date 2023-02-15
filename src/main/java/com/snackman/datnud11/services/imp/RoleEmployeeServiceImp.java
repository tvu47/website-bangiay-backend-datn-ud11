package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.repo.RoleEmployeeRepository;
import com.snackman.datnud11.services.RoleEmployeeService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleEmployeeServiceImp implements RoleEmployeeService {
    @Autowired
    RoleEmployeeRepository roleEmployeeRepository;
    @Override
    public RoleUser checkRoleEmployeeExist(Long id) throws CustomNotFoundException {
        Optional<RoleUser> roleEmployeeOptional = roleEmployeeRepository.findById(id);
        if (roleEmployeeOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return roleEmployeeOptional.get();
    }
}
