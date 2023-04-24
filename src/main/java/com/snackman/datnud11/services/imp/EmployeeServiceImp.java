package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.request.EmployeeRequest;
import com.snackman.datnud11.entity.Employee;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.repo.EmployeeRepository;
import com.snackman.datnud11.repo.RoleUserRepo;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.EmployeeService;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.utils.TimeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee addEmployee(EmployeeRequest employee) {
        if (existUsernameEmployee(employee.getUsername())){
            throw new RuntimeException("username existed!");
        }
        userService.IsUserExist(employee.getUsername());

        userService.createUsers(employee.getUsername(), passwordEncoder.encode(employee.getPassword()));
        RoleUser roleUser = new RoleUser();
        roleUser.setUsername(employee.getUsername());
        roleUser.setRole("EMPLOYEE_ROLE");
        roleUser.setStatus(true);
        roleUser.setCreateTime(new Date());
        userService.createRoleUser(roleUser);
        employee.setCreateTime(new Date());
        employee.setBirthday(TimeUtil.strToDate(employee.getBirthday2(), "yyyy-MM-dd"));
        Employee employee1 = new Employee(employee);
        return employeeRepository.save(employee1);
    }

    private boolean existUsernameEmployee(String username){
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> getEmployees() {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        return false;
    }
}
