package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.request.CustomerRequest;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.BillDetailResponse;
import com.snackman.datnud11.responses.CustomerResponse;
import com.snackman.datnud11.responses.HistoryResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.HistoryService;
import com.snackman.datnud11.services.auth.UserAuth;
import com.snackman.datnud11.utils.DateUtils;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {
	@Autowired
	CustomersRepository customersRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DateUtils dateUtils;
	@Autowired
	private HistoryService historyService;
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getCustomers() {
		return new ResponseEntity<>(this.customerService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Customers> createCustomers(@RequestBody Customers customers) {
		return new ResponseEntity<>(customerService.createCustomer(customers), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Customers> updateCustomerById(@RequestBody CustomerRequest customers)
			throws CustomNotFoundException {
		System.out.println("----update customer begin----");
		log.info("update body: {}", customers);
		customerService.checkCustomerExist(customers.getId());
		Customers customers1 = new Customers();
		customers1.setEmail(customers.getEmail());
		customers1.setFirstName(customers.getFirstName());
		customers1.setLastName(customers.getLastName());
		customers1.setId(customers.getId());
		customers1.setAddress(customers.getAddress());
		customers1.setGender(Integer.parseInt(customers.getGender()));
		customers1.setDateOfBirth(customers.getDateOfBirth());
		customers1.setPhoneNumber(customers.getPhone());
		return new ResponseEntity<>(customerService.updateCustomer(customers1), HttpStatus.CREATED);
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
											@RequestParam(name = "password") String password,
											@RequestParam(name = "phone") String phoneNumber,
											@RequestParam(name = "date") String birthday) {

		return new ResponseEntity<>(customerService.register(username, password, phoneNumber,dateUtils.stringToDate(birthday)), HttpStatus.CREATED);
	}

	@GetMapping("/history")
	public ResponseEntity<Map<Long, List<BillDetailResponse>>> getHistory() throws UserNotfoundException {
		UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<>(historyService.getHistoryPerchaseOfCustomer(userAuth.getUsername()), HttpStatus.CREATED);
	}
}
