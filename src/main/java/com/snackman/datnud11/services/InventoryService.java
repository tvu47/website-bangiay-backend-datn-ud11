package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.responses.InventoryResponse;

import java.util.List;

public interface InventoryService {

    Inventory save(Inventory inventory);

    InventoryResponse getProductById(Long id) throws Exception;
}
