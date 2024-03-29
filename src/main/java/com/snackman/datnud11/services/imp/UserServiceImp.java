package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.RoleUserRepo;
import com.snackman.datnud11.repo.TokenJwtRepo;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.services.auth.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleUserRepo roleUserRepo;
    @Autowired
    private TokenJwtRepo tokenJwtRepo;
    @Override
    public Users findUserByUsername(String user) {
        Optional<Users> usersOptional = userRepository.findByUsername(user);
        if (usersOptional.isEmpty()){
            throw new UserNotfoundException("username is not exists !");
        }
        return usersOptional.get();
    }
    public List<String> getListRoleByUsername(String username) {
        List<RoleUser> roleUserOptional = roleUserRepo.findRoleUserByUsername(username);

        if (roleUserOptional.size() == 0){
            throw new RoleNotFoundException("account have not permission");
        }
        List<String> roles = new ArrayList<>();
        roleUserOptional.forEach(roleUser -> roles.add(roleUser.getRole()));
        return roles;
    }
    @Override
    public UserDetails getUserDetailFromDB(String username) {
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

    @Override
    public Users IsUserExist(String username) {
        Users usersOptional = userRepository.findUsersByUsername(username);
        if (usersOptional != null){
            throw new RuntimeException("User exist on database");
        }
        return usersOptional;
    }

    @Override
    public List<RoleUser> IsRoleUserExist(String username) {
        List<RoleUser> roleUserList = roleUserRepo.findRoleUserByUsername(username);
        if (roleUserList.size() != 0){
            throw new RuntimeException("role exist on database");
        }
        return roleUserList;
    }

    @Override
    public Users updateUser(Users users) {
        return userRepository.save(users);
    }

    public void refreshToken(String username){
        tokenJwtRepo.setIsExpired(username);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
