package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = "select * from users where username like :username and status=1",nativeQuery = true)
    Users findUserByUsername(@Param("username") String username);
    @Query(value = "select * from users where username like :username and password like :password and status=1",nativeQuery = true)
    Users login(@Param("username") String username, @Param("password") String password);
}