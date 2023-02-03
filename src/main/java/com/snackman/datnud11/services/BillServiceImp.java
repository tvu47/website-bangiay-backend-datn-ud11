package com.snackman.datnud11.services;

import com.snackman.billservice.repo.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;


}
