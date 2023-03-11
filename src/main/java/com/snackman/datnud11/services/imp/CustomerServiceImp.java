package com.snackman.datnud11.services.imp;

import ch.qos.logback.core.net.server.Client;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.auth.ClientAuth;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Customers findCustomerByEmail(String email) {
        Optional<Customers> customersOptional = customersRepository.findCustomersByEmail(email);
        if (customersOptional.isEmpty()){
            return null;
        }
        return customersOptional.get();
    }

    @Override
    @Cacheable("userDetails")
    public ClientAuth getUserDetailFromDB(String username) {
        System.out.println("loading userdetail ...");
        Customers customers = findCustomerByEmail(username);
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.setEmail(customers.getEmail());
        clientAuth.setPassword(customers.getPassword());
        return clientAuth;
    }


}
