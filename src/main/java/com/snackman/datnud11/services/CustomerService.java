package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.services.imp.CustomerServiceImp;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomerService {
    Customers checkCustomerExist(Long id) throws CustomNotFoundException;
    Customers findCustomerByEmail(String email);
    UserDetails getUserDetailFromDB(String username);
}
