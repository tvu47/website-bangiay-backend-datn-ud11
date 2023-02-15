package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.EmployeeDTO;
import com.snackman.datnud11.dto.RolesDTO;
import com.snackman.datnud11.dto.UsersDTO;
import com.snackman.datnud11.entity.Employee;
import com.snackman.datnud11.repo.EmployeeRepository;
import com.snackman.datnud11.repo.RolesRepository;
import com.snackman.datnud11.services.UserServices;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserServices , UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public EmployeeDTO saveUser(UsersDTO users) {
        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByUsername(users.getUsername());
        if (optionalEmployee.isEmpty()){
            throw new CustomNotFoundException("usename ");
        }
    }

    @Override
    public RolesDTO saveRole(Collection<RolesDTO> rolesDTOCollection) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String role) {

    }

    @Override
    public EmployeeDTO getUserByUsername(String username) {
        return null;
    }

    @Override
    public EmployeeDTO login(String username, String password) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
