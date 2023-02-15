package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    @Query(value = "select * from role_user where user_id=:userId",nativeQuery = true)
    Optional<List<RoleUser>> getIdRoleByIdUser(@Param("userId") Long userId);
}