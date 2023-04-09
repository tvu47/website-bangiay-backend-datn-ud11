package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {

    @Query(value = "select * from bill_details where bill_id = :id", nativeQuery = true)
    List<BillDetails> findByBillId(@Param("id") Long id);

    @Query(value = "select  * from bill_details where bill_id=:ids", nativeQuery = true)
    List<BillDetails> getBillDetailsByIdBill(@Param("ids") Long id);

//    @Query(value = "SELECT new com.snackman.datnud11.entity.Count(COUNT(v) as count, v.productId, v.billDetailId) FROM BillDetails v where v.billId=:ids GROUP BY v.productId")
    @Query(value = "SELECT new com.snackman.datnud11.entity.Count(v.productId, v.billDetailId) FROM BillDetails v where v.billId=:ids")
    List<Count> getIdProduct(@Param("ids") Long id);
}