package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.RolesDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Roles;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.mapStruct.EmployeeMap;
import com.snackman.datnud11.mapStruct.UserMap;
import com.snackman.datnud11.repo.EmployeeRepository;
import com.snackman.datnud11.repo.RoleUserRepository;
import com.snackman.datnud11.repo.RolesRepository;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserServices;
import com.snackman.datnud11.utils.customException.CustomNullException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserServices , UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final RoleUserRepository roleUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMap userMap = Mappers.getMapper(UserMap.class);
    private final EmployeeMap employeeMap = Mappers.getMapper(EmployeeMap.class);

    @Override
    public Users saveUser(UsersDTO users) {
        Users user = new Users();
        user.setUsername(users.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        return userRepository.save(user);
    }

    @Override
    public RolesDTO saveRole(Collection<RolesDTO> rolesDTOCollection) {
        return null;
    }
    @Override
    public void addRoleToUser(String username, String role) {
    }

    @Override
    public UsersDTO getUserByUsername(String username) {

        Users users = userRepository.findUserByUsername(username);
        if (users==null){
            throw new CustomNullException(username+ ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return userMap.shouldMapUserToUserDto(users);
    }

    @Override
    public EmployeeDTO employeeLogin(String username, String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (username == user.getUsername() && passwordEncoder.encode(password) == user.getPassword()){
            log.info("login successfully!");
            return employeeMap.shouldMapEmployeeToDto(employeeRepository.findEmployeeByUsername(username));
        }
        log.error("login false!");
        throw new CustomNullException(username+ErrorMessage.ERROR_MESSAGE_LOGIN_FALSE.toString());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usersOptional = userRepository.findUserByUsername(username);
        if (usersOptional == null){
            throw new CustomNullException(username+ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        Optional<List<RoleUser>> roleUserList = roleUserRepository.getIdRoleByIdUser(usersOptional.getId());
        if (roleUserList.isEmpty()){
            throw new CustomNullException(username+ErrorMessage.ERROR_MESSAGE_PERMISSION_DENIED.toString());
        }
        Optional<List<Roles>> roles = rolesRepository.getRolesByIdRoleList(getListIdRole(roleUserList.get()));
        Collection<SimpleGrantedAuthority>authorities =new ArrayList<>();
        roles.get().stream().forEach(roles1 -> authorities.add(new SimpleGrantedAuthority(roles1.getRoleName())));
        return new User(usersOptional.getUsername(), usersOptional.getPassword(), authorities);
    }

    private List<Long> getListIdRole(List<RoleUser> roleUserList){
        List<Long> longs = new ArrayList<>();
        roleUserList.stream().forEach(roleUser -> longs.add(roleUser.getRoleId()));
        return longs;
    }
}
