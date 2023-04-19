package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.EmailConstant;
import com.snackman.datnud11.consts.Gender;
import com.snackman.datnud11.dto.request.CustomerRequest;
import com.snackman.datnud11.dto.request.CustomerRequest1;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.EmailSenderService;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.services.auth.UserAuth;
import com.snackman.datnud11.utils.CommonUtils;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Customers checkCustomerExist(Long id) throws CustomNotFoundException {
        Optional<Customers> customersOptional = customersRepository.findById(id);
        if (customersOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return customersOptional.get();
    }

    @Override
    public boolean checkEmailExist(String email) throws UserExistedException {
        Optional<Customers> customersOptional = customersRepository.findCustomersByEmail(email);
        if (customersOptional.isPresent()){
            throw new UserExistedException("Email have been registed..."+ email);
        }
        return true;
    }
    @Override
    public boolean checkPhoneNumberExist(String phoneNumber) throws UserExistedException {
        Optional<Customers> customersOptional = customersRepository.findCustomersByPhoneNumber(phoneNumber);
        if (customersOptional.isPresent()){
            throw new UserExistedException("phone number have been register..."+ phoneNumber);
        }
        return true;
    }

    @Override
    public Customers createCustomer(Customers customers) {
        Customers customersOptional = customersRepository.save(customers);
        if (customersOptional !=null){
            return customersOptional;
        }
        return null;
    }

    @Override
    public Customers storeCustomer(CustomerRequest1 customers) {
        try {
            String username = customers.getEmail();
            // email khong ton tai trong db: checkEmailExist=true
            userService.IsRoleUserExist(username);
            userService.IsUserExist(username);
            if (checkEmailExist(username) && checkPhoneNumberExist(customers.getPhone())){
                //create users in db
                userService.createUsers(username, passwordEncoder.encode(customers.getPassword()));
                // create role_user in db
                RoleUser roleUser = new RoleUser();
                roleUser.setRole("CLIENT_ROLE");
                roleUser.setUsername(username);
                roleUser.setStatus(true);
                userService.createRoleUser(roleUser);
                // create customer in db

                Customers customers1 = new Customers(customers);
                customers1.setCreateTime(new Date());
                Customers customers2 = customersRepository.save(customers1);
//                send gmail to customer
                emailSenderService.sendEmail(username,
                        EmailConstant.EMAIL_REGISTER_SUBJECT,
                        EmailConstant.EMAIL_REGISTER_BODY);
                return customers2;
            }
        } catch (UserExistedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("store customer fail");
    }

    @Override
    public Customers updateCustomer(Customers customers) {
        Customers customersOptional = customersRepository.save(customers);
        if (customersOptional !=null){
            return customersOptional;
        }
        return null;
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customers> list = this.customersRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customers customer : list){
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setFirstName(customer.getFirstName());
            customerResponse.setLastName(customer.getLastName());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setGender(Gender.findGender(customer.getGender()).name);
            customerResponse.setPhone(customer.getPhoneNumber());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setCreateTime(customer.getCreateTimeFormat());
            customerResponse.setActive(customer.getStatus());
            customerResponses.add(customerResponse);
        }

        return customerResponses;
    }

    @Override
    public Customers findById(Long id) throws Exception {
        Optional<Customers> optional = this.customersRepository.findById(id);
        if(optional.isEmpty()){
            throw new Exception("");
        }
        return optional.get();
    }

    @Override
    public Customers findCustomerByEmail(String email) throws UserNotfoundException {
        Optional<Customers> customersOptional = customersRepository.findCustomersByEmail(email);
        if (customersOptional.isEmpty()){
            throw new UserNotfoundException("email is not found: "+email);
        }
        return customersOptional.get();
    }

    @Override
    public CustomerResponse login(String username) throws UserNotfoundException {
        Optional<Customers> customers = customersRepository.findCustomersByEmail(username);
        if (customers.isEmpty()){
            throw new UserNotfoundException("Customer not found in database");
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setEmail(customers.get().getEmail());
        customerResponse.setId(customers.get().getId());
        customerResponse.setPhone(customers.get().getPhoneNumber());
        customerResponse.setGender(String.valueOf(customers.get().getGender()));
        customerResponse.setAddress(customers.get().getAddress());
        customerResponse.setFirstName(customers.get().getFirstName());
        customerResponse.setLastName(customers.get().getLastName());
        customerResponse.setCreateTime(customers.get().getCreateTimeFormat());
        customerResponse.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(customers.get().getDateOfBirth()));
        return customerResponse;
    }

    @Override
    public boolean forgetPassword(String username){
        String newRandomPassword = CommonUtils.getRandomPassword();

        try {
            userService.findUserByUsername(username);
            if (checkEmailExist(username)){
                emailSenderService.sendEmail(username, EmailConstant.EMAIL_FOGOTPASSWORD_SUBJECT, EmailConstant.EMAIL_FOGOTPASSWORD_BODY+"\n Your new password is "+newRandomPassword);
                Users user = userService.findUserByUsername(username);
                user.setPassword(passwordEncoder.encode(newRandomPassword));
                userService.updateUser(user);
                return true;
            }
        } catch (UserExistedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean changePassword(String newPassword, String prePassword) {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userService.findUserByUsername(userAuth.getUsername());
        try {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CLIENT_ROLE"));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAuth,prePassword,authorities);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (Exception e){
            throw new BadCredentialsException("present password is not correct");
        }

        if (!user.getPassword().equals(prePassword)){
            throw new RuntimeException("Present password is not correct. Please try again..");
        }
        try {
            if (checkEmailExist(user.getUsername())){
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(user);
                return true;
            }
        } catch (UserExistedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Boolean register(String username, String password, String phoneNumber, Date birthday) {
        try {
            // email khong ton tai trong db: checkEmailExist=true
            userService.IsRoleUserExist(username);
            userService.IsUserExist(username);
            if (checkEmailExist(username) && checkPhoneNumberExist(phoneNumber)){
                //create users in db
                userService.createUsers(username, passwordEncoder.encode(password));
                // create role_user in db
                RoleUser roleUser = new RoleUser();
                roleUser.setRole("CLIENT_ROLE");
                roleUser.setUsername(username);
                roleUser.setStatus(true);
                userService.createRoleUser(roleUser);
                // create customer in db
                Customers customers = new Customers();
                customers.setEmail(username);
                customers.setStatus(true);
                customers.setPhoneNumber(phoneNumber);
                customers.setDateOfBirth(birthday);
                customers.setCreateTime(new Date());
                customers.setGender(0);
                customersRepository.save(customers);
//                send gmail to customer
                emailSenderService.sendEmail(username,
                        EmailConstant.EMAIL_REGISTER_SUBJECT,
                        EmailConstant.EMAIL_REGISTER_BODY);
                return true;
            }
        } catch (UserExistedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public String logout() {
        String status = "false";
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            SecurityContextHolder.clearContext();
            context.setAuthentication(null);
            status = "true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
