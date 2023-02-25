package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(value = "select role_name from roles where role_id in(:roleIds)",nativeQuery = true)
    List<String> getRolesByIdRoleList(@Param("roleIds") List<Long> roleIds);
}