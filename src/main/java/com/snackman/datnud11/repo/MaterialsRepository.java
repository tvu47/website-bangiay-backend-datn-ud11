package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long> {
    @Query(value = "select  * from materials where material_id in(:ids)", nativeQuery = true)
    List<Materials> getMaterialsByListId(@Param("ids") List<Long> materialListId);

    Optional<Materials> findMaterialsByMaterialName(String materialName);
}