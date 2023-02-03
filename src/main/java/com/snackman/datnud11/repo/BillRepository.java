package com.snackman.datnud11.repo;

import com.bangiay.snackman.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Long, Bill> {
}
