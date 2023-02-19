package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select * from employee where username like :username",nativeQuery = true)
    Employee findEmployeeByUsername(@Param("username") String username);
}