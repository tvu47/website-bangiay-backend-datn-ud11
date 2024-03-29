package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.request.CustomerRequest;
import com.snackman.datnud11.dto.request.CustomerRequest1;
import com.snackman.datnud11.dto.request.CustomerRequest2;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.services.imp.CustomerServiceImp;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

public interface CustomerService {
    Customers checkCustomerExist(Long id) throws CustomNotFoundException;

    boolean checkEmailExist(String email) throws UserExistedException;

    boolean checkPhoneNumberExist(String email) throws UserExistedException;

    Customers createCustomer(Customers customers);
    Customers storeCustomer(CustomerRequest1 customers);
    Customers storeCustomerAdvand(CustomerRequest2 customers);
    Customers updateCustomer(Customers customers);

    List<CustomerResponse> findAll();

    Customers findById(Long id) throws Exception;

    Customers findCustomerByEmail(String email) throws UserNotfoundException;
    CustomerResponse login(String username) throws UserNotfoundException;
    boolean forgetPassword(String username);
    boolean changePassword(String password, String prePassword);
    Boolean register(String username, String password, String phoneNumber, Date birthday);

    String logout();
}
