package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.SearchProducts;
import com.snackman.datnud11.dto.ProductDTO;
import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.responses.ProductManagerResponse;
import com.snackman.datnud11.responses.ProductResponse;
import com.snackman.datnud11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private ImageService imageService;

    @Override
    public Products save(Products products) {
        return this.repo.save(products);
    }

    @Override
    public Products save(ProductDTO productDTO) {
        Products product = this.repo.save(new Products(productDTO));
        String[] images = productDTO.getImages().split("\n");
        for(String imageLink : images){
            Images img = new Images();
            img.setProductId(product.getId());
            img.setImageUrl(imageLink);
            img.setStatus(true);
            this.imageService.save(img);
        }
        return null;
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
        List<Category> categories = this.categoryService.findAll();
        List<Materials> materials = this.materialService.findAll();
        List<Inventory> inventoriesList = this.inventoryService.findAll();
        for(Products product : products){
            ProductManagerResponse response = new ProductManagerResponse();
            response.setId(product.getId());
            response.setName(product.getProductName());
            response.setContent(product.getContent());
            response.setStatus(product.getStatus());
            response.setManufactory(product.getManufactureAddress());
            response.setCategory(this.categoryService.findById(categories, product.getCategoryId()).getCategoryName());
            response.setMaterial(this.materialService.findById(materials, product.getMaterialId()).getMaterialName());

            List<Inventory> inventories = this.inventoryService.findByProductId(inventoriesList, product.getId());
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

    @Override
    public List<ProductResponse> listAllProductManager() throws Exception {
        List<ProductResponse> list = new ArrayList<>();
        List<Products> products = this.findAll();
        List<Category> categories = this.categoryService.findAll();
        List<Materials> materials = this.materialService.findAll();
        for(Products product : products){
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getProductName());
            response.setContent(product.getContent());
            response.setStatus(product.getStatus());
            response.setManufactory(product.getManufactureAddress());
            response.setCategory(this.categoryService.findById(categories, product.getCategoryId()).getCategoryName());
            response.setMaterial(this.materialService.findById(materials, product.getMaterialId()).getMaterialName());
            list.add(response);
        }
        return list;
    }
}
