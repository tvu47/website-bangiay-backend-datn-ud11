package com.snackman.datnud11.controller;

import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.repo.BillRepository;
import com.snackman.datnud11.responses.BillResponse;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.generic.GenericObjFindById;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@Slf4j
public class BillController {

	@Autowired
	BillRepository billRepository;

	@Autowired
	private BillService billService;

	@GetMapping
	public ResponseEntity<List<BillResponse>> getBills() {
		return new ResponseEntity<>(this.billService.getAllBill(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
		return new ResponseEntity<>(billRepository.save(bill), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Bill> updateBillById(@RequestBody Bill billUpdate) throws CustomNotFoundException {
		billService.checkBillExist(billUpdate.getId());
		return new ResponseEntity<>(billRepository.save(billUpdate), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBillById(@PathVariable(name = "id") Long id) throws CustomNotFoundException {
		Bill bill = billService.checkBillExist(id);
		billRepository.delete(bill);
		return new ResponseEntity<>("Delete Successfully!", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/accept/{id}")
	public ResponseEntity<NoticeResponse> acceptBill(@PathVariable(name = "id") Long id) throws Exception {
		this.billService.acceptBill(id);
		return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(),"Duyệt đơn hàng thành công!"), HttpStatus.OK);
	}

}
