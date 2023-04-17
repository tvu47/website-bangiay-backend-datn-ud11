package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.InventoryImportExcelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryImportExcelRepo extends JpaRepository<InventoryImportExcelDTO, Integer> {
}
