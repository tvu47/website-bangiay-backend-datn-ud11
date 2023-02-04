package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface CustomerService {
    Customers checkCustomerExist(Long id) throws CustomNotFoundException;
}
