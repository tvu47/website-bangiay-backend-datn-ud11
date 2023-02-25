package com.snackman.datnud11.services.imp;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.repo.RoleUserRepository;
import com.snackman.datnud11.repo.RolesRepository;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserServices;
import com.snackman.datnud11.services.auth.UserAuth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserServices {
    private final RoleUserRepository roleUserRepository;
    private final UserRepository userRepository;

    @Override
    public List<String> getRoleListByUsername(String username) {
        return roleUserRepository.getRoleListByUsername(username);
    }

    public Users findUserByUsername(String username){
        Users users = userRepository.findUserByUsername(username);
        if (users == null){
            throw new UsernameNotFoundException("User not found !!!");
        }
        return users;
    }

    @Override
    public UserAuth getUserDetailFromDB(String username) {
        Users users = findUserByUsername(username);
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(users.getUsername());
        userAuth.setPassword(users.getPassword());
        userAuth.setRole(getRoleListByUsername(username));
        return userAuth;
    }
}
