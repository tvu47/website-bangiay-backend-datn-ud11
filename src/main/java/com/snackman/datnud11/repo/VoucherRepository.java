package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query(value = "select * from vouchers where (now() between start_time and end_time) and quantity > 0 " +
            "and status = 1 and id not in (select voucher_id from bill where customer_id = :customerId)", nativeQuery = true)
    List<Voucher> findAllAvailable(@Param(value = "customerId") Long id);

    @Query(value = "select * from vouchers where code = :code", nativeQuery = true)
    Voucher findByCode(@Param(value = "code") String code);
}
