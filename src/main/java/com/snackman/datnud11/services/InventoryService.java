package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.responses.InventoryResponse;

import java.util.List;

public interface InventoryService {

    Inventory save(Inventory inventory);

    Inventory findBySku(String sku);

    InventoryResponse getProductById(Long id) throws Exception;

    boolean validProductsOrder(List<PaymentDTO.ProductOrder> productOrders);
}
