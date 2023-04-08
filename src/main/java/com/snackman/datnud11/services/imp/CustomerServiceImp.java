package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.Gender;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.EmailSenderService;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        customerResponse.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(customers.get().getDateOfBirth()));
        return customerResponse;
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
                //send gmail to customer
//                emailSenderService.sendEmail(username,
//                        "SnackMan Register Account",
//                        "you have been register on snackman.");
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
