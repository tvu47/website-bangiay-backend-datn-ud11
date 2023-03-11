package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findCustomersByEmail(String email);
}