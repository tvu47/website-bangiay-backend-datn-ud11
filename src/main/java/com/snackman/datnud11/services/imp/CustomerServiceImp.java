package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.Gender;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.auth.UserAuth;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public boolean checkEmailExist(String email) throws UserExistedException {
        Optional<Customers> customersOptional = customersRepository.findCustomersByEmail(email);
        if (customersOptional.isPresent()){
            throw new UserExistedException("Email have been registed..."+ email);
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



}
