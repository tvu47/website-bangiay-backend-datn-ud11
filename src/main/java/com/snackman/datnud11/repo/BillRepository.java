package com.snackman.datnud11.repo;


import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.responses.Count;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "select * from bill where customer_id = :id and status=:statuss order by create_time desc", nativeQuery = true)
    List<Bill> findBillByCustomerId(@Param("id") Long id, @Param("statuss") int status);

    @Query(value = "select * from bill order by status asc, create_time desc", nativeQuery = true)
    List<Bill> findAllOrderByStatus();

    @Query(value = "select * from bill where status = :statuss order by create_time desc", nativeQuery = true)
    List<Bill> findAllByStatus(@Param("statuss") int status);

    List<Bill> findAllBillByCustomerId(@Param("id") Long id);

    @Query(value = "select * from bill where status=:statuss order by create_time desc", nativeQuery = true)
    List<Bill> findBillByStatus(@Param("statuss") int status);
    @Query(value = "select * from bill where status=:statuss and create_time between :begin and :to", nativeQuery = true)
    List<Bill> thongKeHoaDon(@Param("begin") @Temporal(TemporalType.TIMESTAMP) Date startDate,
                             @Param("to") @Temporal(TemporalType.TIMESTAMP) Date endDate,
                             @Param("statuss") int status);

    @Query(value = "SELECT new com.snackman.datnud11.responses.Count(COUNT(b.id), SUM(b.totalPrice)) FROM Bill b where b.status=:statuss and b.createTime between :begin and :to")
    Count tinhTong(@Param("begin") @Temporal(TemporalType.TIMESTAMP) Date startDate,
                   @Param("to") @Temporal(TemporalType.TIMESTAMP) Date endDate,
                   @Param("statuss") int status);

    @Query(value = "SELECT new com.snackman.datnud11.responses.Count(COUNT(b.id), SUM(b.totalPrice)) FROM Bill b WHERE b.status=:statuss")
    Count tinhTongAll(@Param("statuss") int status);
}
