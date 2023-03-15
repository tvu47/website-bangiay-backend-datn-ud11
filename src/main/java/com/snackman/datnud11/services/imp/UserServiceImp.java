package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.RoleUserRepo;
import com.snackman.datnud11.repo.RoleRepo;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.services.auth.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleUserRepo roleUserRepo;
    private final RoleRepo roleRepo;
    @Override
    public Users findUserByUsername(String user) throws UserNotfoundException {
        Optional<Users> usersOptional = userRepository.findByUsername(user);
        if (usersOptional.isEmpty()){
            throw new UserNotfoundException("user is not found: "+user);
        }
        return usersOptional.get();
    }

    public List<String> getListRoleByUsername(String username) throws RoleNotFoundException {
        Optional<List<RoleUser>> roleUserOptional = roleUserRepo.findRoleUserByUsername(username);

        if (roleUserOptional.isEmpty()){
            throw new RoleNotFoundException("Role is not defined...");
        }
        List<String> roles = new ArrayList<>();
        roleUserOptional.get().forEach(roleUser -> roles.add(roleUser.getRole()));
        return roles;
    }

    @Override
    public UserDetails getUserDetailFromDB(String username) throws UserNotfoundException, RoleNotFoundException {
        System.out.println("loading userdetail ...");
        Users users = findUserByUsername(username);
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(users.getUsername());
        userAuth.setPassword(users.getPassword());
        userAuth.setRoles(getListRoleByUsername(username));
        return userAuth;
    }
}
