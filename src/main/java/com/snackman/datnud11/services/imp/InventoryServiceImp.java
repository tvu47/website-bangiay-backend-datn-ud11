package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.repo.InventoryRepository;
import com.snackman.datnud11.responses.InventoryResponse;
import com.snackman.datnud11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImp implements InventoryService {

    @Autowired
    private InventoryRepository repo;

    @Autowired
    private ZProductService zProductService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Override
    public Inventory save(Inventory inventory) {
        return this.repo.save(inventory);
    }

    @Override
    public Inventory findBySku(String sku) {
        return this.repo.findBySku(sku);
    }

    @Override
    public List<Inventory> findByProductId(Long id) {
        return this.repo.findByProductId(id);
    }

    @Override
    public InventoryResponse getProductById(Long id) throws Exception {
        List<Inventory> inventory = this.repo.findByProductId(id);
        return this.convertInventoryToInventoryResponse(inventory);
    }

    @Override
    public boolean validProductsOrder(List<PaymentDTO.ProductOrder> productOrders) {
        for(PaymentDTO.ProductOrder productOrder: productOrders){
            if(productOrder.getQuantity() < 1){
                return false;
            }
            Inventory inventory = this.findBySku(productOrder.getSku());
            if(inventory == null || inventory.getQuatity() < productOrder.getQuantity()){
                return false;
            }
        }
        return true;
    }

    private InventoryResponse convertInventoryToInventoryResponse(List<Inventory> list) throws Exception {
        InventoryResponse response = new InventoryResponse();
        response.setProducts(this.zProductService.findByProductId(list.get(0).getProductId()).get(0));
        List<Colors> colorsList = this.colorService.findAll();
        List<Size> sizeList = this.sizeService.findAll();
        for(Inventory inventory : list){
            InventoryResponse.ColorOption colorOptionTemp = null;
            for(InventoryResponse.ColorOption colorOption : response.getColorOptions()){
                if(colorOption.getColors().getId() == inventory.getColor()){
                    colorOptionTemp = colorOption;
                    break;
                }
            }
            if(colorOptionTemp == null){
                colorOptionTemp = new InventoryResponse.ColorOption();
                colorOptionTemp.setColors(this.colorService.findById(colorsList, inventory.getColor()));
                response.getColorOptions().add(colorOptionTemp);
            }
            InventoryResponse.SizeOption sizeOption = new InventoryResponse.SizeOption();
            sizeOption.setSize(this.sizeService.findById( sizeList, inventory.getSize()));
            sizeOption.setPrice(inventory.getPrice());
            sizeOption.setQuantity(inventory.getQuatity());
            colorOptionTemp.getSizeOptions().add(sizeOption);
        }
        return response;
    }
}
