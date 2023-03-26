package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String user);
    @Query(value = "select * from users where username=:user", nativeQuery = true)
    Users findUsersByUsername(@Param("user") String username);
}
