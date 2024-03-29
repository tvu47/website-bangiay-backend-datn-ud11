package com.snackman.datnud11.controller;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.responses.NoticeResponse;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private BillService billService;

    @PostMapping()
    public ResponseEntity<NoticeResponse> payment(@RequestBody PaymentDTO paymentDTO) throws Exception {
        System.out.println(paymentDTO.toString());
        if(!this.productDetailService.validProductsOrder(paymentDTO.getProductsOrder())){
            return new ResponseEntity("",HttpStatus.OK);
        }
        return new ResponseEntity<>(new NoticeResponse(HttpStatus.OK.value(), "Đặt hàng thành công!",
                this.billService.payment(paymentDTO)),HttpStatus.OK);
    }


}
