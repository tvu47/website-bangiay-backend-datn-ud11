package com.snackman.datnud11.repo;

import com.bangiay.snackman.entity.RoleEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEmployeeRepository extends JpaRepository<RoleEmployee, Long> {
}