package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {

    @Query(value = "select * from bill_details where bill_id = :id", nativeQuery = true)
    List<BillDetails> findByBillId(@Param("id") Long id);

}