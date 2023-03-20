package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.TokenJwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJwtRepo extends JpaRepository<TokenJwt, Long> {
    @Query(value = "select top 1 * from token_jwt where username like :user and is_expire like 1", nativeQuery = true)
    TokenJwt findTokenJwtByUsernameAndExpiress(@Param("user") String username);
    @Query(value = "update token_jwt set is_expire = 0 where username like :user and is_expire like 1", nativeQuery = true)
    TokenJwt setIsExpired(@Param("user") String username);
}
