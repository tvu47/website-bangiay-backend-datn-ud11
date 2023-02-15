package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    CustomersRepository customersRepository;
    @Override
    public Customers checkCustomerExist(Long id) throws CustomNotFoundException {
        Optional<Customers> customersOptional = customersRepository.findById(id);
        if (customersOptional.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }
        return customersOptional.get();
    }
}
