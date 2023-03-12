package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.auth.ClientAuth;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public boolean checkEmailExist(String email) throws UserExistedException {
        Optional<Customers> customersOptional = customersRepository.findCustomersByEmail(email);
        if (customersOptional.isPresent()){
            throw new UserExistedException("Email have been registed..."+ email);
        }
        return true;
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
    @Cacheable("userDetails")
    public ClientAuth getUserDetailFromDB(String username) throws UserNotfoundException {
        System.out.println("loading userdetail ...");
        Customers customers = findCustomerByEmail(username);
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.setEmail(customers.getEmail());
        clientAuth.setPassword(customers.getPassword());
        return clientAuth;
    }


}
