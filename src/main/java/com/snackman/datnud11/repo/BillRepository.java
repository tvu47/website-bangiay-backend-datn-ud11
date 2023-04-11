package com.snackman.datnud11.repo;


import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "select * from bill where customer_id = :id and status=:statuss", nativeQuery = true)
    List<Bill> findBillByCustomerId(@Param("id") Long id, @Param("statuss") int status);
    @Query(value = "select * from bill where customer_id = :id", nativeQuery = true)
    List<Bill> findAllBillByCustomerId(@Param("id") Long id);
}
