package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {
}