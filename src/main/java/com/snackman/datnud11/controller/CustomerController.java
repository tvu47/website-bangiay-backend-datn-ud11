package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	@Autowired
	CustomersRepository customersRepository;
	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getCustomers() {
		return new ResponseEntity<>(this.customerService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Customers> createCustomers(@RequestBody Customers customers) {
		return new ResponseEntity<>(customersRepository.save(customers), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Customers> updateCustomerById(@RequestBody Customers customers)
			throws CustomNotFoundException {
		customerService.checkCustomerExist(customers.getId());
		return new ResponseEntity<>(customersRepository.save(customers), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> DeleteCustomersById(@PathVariable(name = "id") Long id)
			throws CustomNotFoundException {
		Customers customers = customerService.checkCustomerExist(id);
		customersRepository.delete(customers);
		return new ResponseEntity<>("Delete Successfully!", HttpStatus.NO_CONTENT);
	}
	@PostMapping("/login")
	public ResponseEntity<CustomerResponse> login(@RequestAttribute(value = "token",required = false) String token,
												  @RequestParam(value = "username") String username) throws UserNotfoundException {
		CustomerResponse customerResponse = customerService.login(username);
		customerResponse.setToken(token);
		return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
	}
	@PostMapping("/logout")
	public ResponseEntity<Boolean> logout() {
		return new ResponseEntity<>(customerService.logout(), HttpStatus.CREATED);
	}
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestParam(name = "username") String username,
											@RequestParam(name = "password") String password) {
		return new ResponseEntity<>(customerService.register(username, password), HttpStatus.CREATED);
	}
}
