package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.TokenJwt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJwtRepo extends JpaRepository<TokenJwt, Long> {
    @Query(value = "select * from token_jwt where username=:user and is_expire=1", nativeQuery = true)
    TokenJwt findTokenJwtByUsernameAndExpiress(@Param("user") String username);
    @Transactional
    @Modifying
    @Query(value = "UPDATE token_jwt SET is_expire = 0 WHERE username=:user and is_expire=1", nativeQuery = true)
    void setIsExpired(@Param("user") String username);
}
