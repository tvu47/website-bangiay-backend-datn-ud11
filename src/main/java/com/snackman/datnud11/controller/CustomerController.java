package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.CustomerDTO;
import com.snackman.datnud11.dto.request.CustomerRequest;
import com.snackman.datnud11.dto.request.CustomerRequest1;
import com.snackman.datnud11.dto.request.CustomerRequest2;
import com.snackman.datnud11.dto.request.CustomerRequestAdvand;
import com.snackman.datnud11.entity.Address;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.CustomMessageException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.repo.AddressRepository;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.*;
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
import java.util.Optional;

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
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private AddressRepository addressRepository;

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
		if (customers.getCreateTime() == null){
			customers1.setCreateTime(new Date());
		}
		customers1.setEmail(customers.getEmail());
		customers1.setFirstName(customers.getFirstName());
		customers1.setLastName(customers.getLastName());
		customers1.setId(customers.getId());
		customers1.setAddress(customers.getAddress());
		customers1.setGender(Integer.parseInt(customers.getGender()));
		customers1.setDateOfBirth(customers.getDateOfBirth());
		customers1.setPhoneNumber(customers.getPhone());
		customers1.setStatus(customers.getActive());
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
	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		return new ResponseEntity<>(customerService.logout(), HttpStatus.NO_CONTENT);
	}
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestParam(name = "username") String username,
											@RequestParam(name = "password") String password,
											@RequestParam(name = "phone") String phoneNumber,
											@RequestParam(name = "date") String birthday) {
		return new ResponseEntity<>(customerService.register(username, password, phoneNumber,dateUtils.stringToDate(birthday)), HttpStatus.CREATED);
	}
	@PostMapping("/forgot-password")
	public ResponseEntity<Boolean> forgotPassword(@RequestParam(name = "username") String username) {
		return new ResponseEntity<>(customerService.forgetPassword(username), HttpStatus.CREATED);
	}
	@PostMapping("/change-password")
	public ResponseEntity<Boolean> changePassword(@RequestParam(name = "newPassword") String newPassword,
												  @RequestParam(name = "prePassword") String prePassword) {
		return new ResponseEntity<>(customerService.changePassword(newPassword, prePassword), HttpStatus.OK);
	}
	@PostMapping("/store")
	public ResponseEntity<Customers> storeCustomer(@RequestBody CustomerRequest1 customerRequest){
		return new ResponseEntity<>(customerService.storeCustomer(customerRequest), HttpStatus.CREATED);
	}
	@PostMapping("/store-advand")
	public ResponseEntity<Customers> storeAdvandCustomer(@RequestBody CustomerRequest2 customerRequest){
		return new ResponseEntity<>(customerService.storeCustomerAdvand(customerRequest), HttpStatus.CREATED);
	}
	@PostMapping("/history-bills")
	public ResponseEntity<List<BillResponseHistory>> getBillHistoryByStatus(@RequestParam(name = "status", required = false) String status) {
		UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = customerService.findCustomerByEmail(userAuth.getUsername()).getId();
		return new ResponseEntity<>(historyService.getBillByIdCustomer(id, Integer.parseInt(status)), HttpStatus.CREATED);
	}
	@PostMapping("/history-bill-details")
	public ResponseEntity<List<BillDetailResponse>> getBillDetailByBill(@RequestParam(name = "id", required = false) String idBill) {
		return new ResponseEntity<>(historyService.getBillDetailByBill(Long.valueOf(idBill)), HttpStatus.CREATED);
	}
	@PostMapping("/cancel-bill")
	public ResponseEntity<String> cancelBill(@RequestParam(name = "id", required = false) String idBill) {
		Optional<Bill> bill = billRepository.findById(Long.valueOf(idBill));
		if (bill.isEmpty()){
			throw new RuntimeException("Something wrong happened. Please try again");
		}
		if (bill.get().getStatus()!=0){
			throw new RuntimeException("Hóa đơn không được phép hủy do đã được xác nhận.");
		}
		bill.get().setStatus(2);
		billRepository.save(bill.get());
		return new ResponseEntity<>("Hủy đơn hàng thành công", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/test")
	public ResponseEntity<Customers> updateCustomerByIdAdvand(@RequestBody CustomerRequestAdvand customers)
			throws CustomNotFoundException {
		System.out.println("----update customer begin----");
		log.info("update body: {}", customers);
		customerService.checkCustomerExist(customers.getId());
		Customers customers1 = new Customers();
		if (customers.getCreateTime() == null){
			customers1.setCreateTime(new Date());
		}
		customers1.setEmail(customers.getEmail());
		customers1.setFirstName(customers.getFirstName());
		customers1.setLastName(customers.getLastName());
		customers1.setId(customers.getId());
		customers1.setGender(Integer.parseInt(customers.getGender()));
		customers1.setDateOfBirth(customers.getDateOfBirth());
		customers1.setPhoneNumber(customers.getPhone());
		customers1.setStatus(customers.getActive());



		return new ResponseEntity<>(customerService.updateCustomer(customers1), HttpStatus.CREATED);
	}

}
