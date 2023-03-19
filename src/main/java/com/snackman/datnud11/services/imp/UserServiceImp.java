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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleUserRepo roleUserRepo;
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
    @Cacheable(value = "user_details_from_db")
    public UserDetails getUserDetailFromDB(String username) throws UserNotfoundException, RoleNotFoundException {
        System.out.println("loading userdetail ...");
        Users users = findUserByUsername(username);
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(users.getUsername());
        userAuth.setPassword(users.getPassword());
        userAuth.setRoles(getListRoleByUsername(username));
        return userAuth;
    }
    @Override
    public Users createUsers(String username, String password) {
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setStatus(true);
        Users users1= userRepository.save(users);
        if (users1!=null){
            return users1;
        }
        return null;
    }
    @Override
    public RoleUser createRoleUser(RoleUser roleUser) {
        RoleUser roleUser1 = roleUserRepo.save(roleUser);
        if (roleUser1!=null){
            return roleUser1;
        }
        return null;
    }
}