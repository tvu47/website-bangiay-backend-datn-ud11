package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.SearchProducts;
import com.snackman.datnud11.entity.Inventory;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.responses.ProductManagerResponse;
import com.snackman.datnud11.services.CategoryService;
import com.snackman.datnud11.services.InventoryService;
import com.snackman.datnud11.services.MaterialService;
import com.snackman.datnud11.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductsRepository repo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MaterialService materialService;

    @Lazy
    @Autowired
    private InventoryService inventoryService;

    @Override
    public Products save(Products products) {
        return this.repo.save(products);
    }

    @Override
    public void delete(Products products) {
        this.repo.delete(products);
    }

    @Override
    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Products findById(Long id) throws Exception {
        Optional<Products> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found product id " + id);
        }
        return optional.get();
    }

    @Override
    public List<Products> findAll() {
        return this.repo.findAll();
    }

    @Override
    public List<Products> findByName(String name) {
        return this.repo.findByName(name);
    }

    @Override
    public List<Products> findByCategoryId(Long categoryId) {
        return this.repo.findByCategoryId(categoryId);
    }

    @Override
    public List<Products> findByPriceRange(Double min, Double max) {
        return this.repo.findByPriceRange(min,max);
    }

    @Override
    public List<Products> getAllOrderByPrice(String typeSort) {
        if(typeSort.equals("asc")){
            return this.repo.findOrderPriceASC();
        } else {
            return this.repo.findOrderPriceDESC();
        }
    }

    @Override
    public List<Products> getAllOrderByName(String typeSort) {
        if(typeSort.equals("asc")){
            return this.repo.findOrderByNameASC();
        } else {
            return this.repo.findOrderByNameDESC();
        }
    }

    @Override
    public List<Products> searchProducts(Map<String, List<String>> params) throws Exception{
        if(params.containsKey(SearchProducts.FIND_BY_PRODUCT_NAME.paramName)){
            String productName = params.get(SearchProducts.FIND_BY_PRODUCT_NAME.paramName).get(0);
            return this.findByName(productName);
        }

        if(params.containsKey(SearchProducts.FIND_BY_CATEGORY_ID.paramName)){
            Long categoryId = Long.parseLong(params.get(SearchProducts.FIND_BY_CATEGORY_ID.paramName).get(0));
            return this.findByCategoryId(categoryId);
        }

        if(params.containsKey(SearchProducts.FIND_BY_PRICE_RANGE.paramName)){
            List<String> range = params.get(SearchProducts.FIND_BY_PRICE_RANGE.paramName);
            return this.findByPriceRange(Double.parseDouble(range.get(0)), Double.parseDouble(range.get(1)));
        }
        if(params.containsKey(SearchProducts.ORDER_BY_PRICE.paramName)){
            String order = params.get(SearchProducts.ORDER_BY_PRICE.paramName).get(0);
            return this.getAllOrderByPrice(order);
        }
        if(params.containsKey(SearchProducts.ORDER_BY_NAME.paramName)){
            String order = params.get(SearchProducts.ORDER_BY_NAME.paramName).get(0);
            return this.getAllOrderByName(order);
        }

        throw new Exception("Not found products");
    }

    @Override
    public List<ProductManagerResponse> findAllProductsManager() throws Exception {
        List<ProductManagerResponse> list = new ArrayList<>();
        List<Products> products = this.findAll();
        for(Products product : products){
            ProductManagerResponse response = new ProductManagerResponse();
            response.setId(product.getId());
            response.setName(product.getProductName());
            response.setContent(product.getContent());
            response.setStatus(product.getStatus());
            response.setManufactory(product.getManufactureAddress());
            response.setCategory(this.categoryService.findById(product.getCategoryId()).getCategoryName());
            response.setMaterial(this.materialService.findById(product.getMaterialId()).getMaterialName());

            List<Inventory> inventories = this.inventoryService.findByProductId(product.getId());
            List<ProductManagerResponse.Inventory> listInventories = new ArrayList<>();
            response.setInventories(listInventories);
            for(Inventory inventory : inventories){
                ProductManagerResponse.Inventory i = new ProductManagerResponse.Inventory();
                i.setId(inventory.getId());
                i.setSku(inventory.getSku());
                i.setColor(inventory.getColorName());
                i.setSize(inventory.getSizeName());
                i.setQuantity(inventory.getQuatity());
                i.setPrice(inventory.getPrice());
                listInventories.add(i);
            }
            list.add(response);
        }
        return list;
    }
}
