package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.repo.RoleUserRepo;
import com.snackman.datnud11.services.RoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleUserServiceImp implements RoleUserService {
    private final RoleUserRepo roleUserRepo;

    @Override
    public RoleUser createRoleUser(RoleUser roleUser) {
        RoleUser roleUser1 = roleUserRepo.save(roleUser);
        if (roleUser1!=null){
            return roleUser1;
        }
        return null;
    }
}
