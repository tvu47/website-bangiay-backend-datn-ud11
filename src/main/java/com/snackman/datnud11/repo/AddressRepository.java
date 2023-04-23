package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findAddressByCustomerId(Long customerId);
}