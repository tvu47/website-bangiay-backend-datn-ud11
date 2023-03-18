package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.entity.Discounts;
import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.entity.Products;
import com.snackman.datnud11.repo.CategoryRepository;
import com.snackman.datnud11.repo.DiscountsRepository;
import com.snackman.datnud11.repo.MaterialsRepository;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.responses.ProductsResponse;
import com.snackman.datnud11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZProductServiceImp implements ZProductService {

    @Autowired
    DiscountsRepository discountsRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MaterialsRepository materialsRepository;
    @Autowired
    ProductService productService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ColorService colorService;

    @Override
    public List<ProductsResponse> getAllProductResponses() {
        return formatProductToProductResponse(productService.findAll());
    }

    @Override
    public List<ProductsResponse> findByProductId(Long id) throws Exception {
        List<Products> products = new ArrayList<>();
        products.add(this.productService.findById(id));
        List<ProductsResponse> productsResponses = this.formatProductToProductResponse(products);
        return productsResponses;
    }

    public List<ProductsResponse> formatProductToProductResponse(List<Products> productsList){
        List<ProductsResponse> productsResponsesList = new ArrayList<>();
        // get id list
        Map<String, List<Long>> idList = getListIds(productsList);

        Map<Long, Materials> materialsMap = getMaterialByListId(idList.get("material"));
        Map<Long, Category> categoryMap= getCategoryByListId(idList.get("category"));

        productsList.stream().forEach(products -> {
            ProductsResponse productsResponse = new ProductsResponse(products);
            productsResponse.setImages(this.imageService.getImagesByProductId(products.getId()));
            productsResponse.setCategory(categoryMap.get(products.getCategoryId()));
            productsResponse.setMaterials(materialsMap.get(products.getMaterialId()));
            productsResponsesList.add(productsResponse);
        });
        return productsResponsesList;
    }

    private Map<String, List<Long>> getListIds(List<Products> productsList){
        List<Long> materialListId = new ArrayList<>();
        List<Long> categoryListId = new ArrayList<>();

        productsList.stream().forEach(products -> {
            materialListId.add(products.getMaterialId());
            categoryListId.add(products.getCategoryId());
        });
        Map<String, List<Long>> listMapIds = new HashMap<>();
        listMapIds.put("material", materialListId);
        listMapIds.put("category", materialListId);
        listMapIds.put("discount", materialListId);
        return  listMapIds;
    }

    private Map<Long, Materials> getMaterialByListId(List<Long> longIdList){
        Map<Long, Materials> materialsMap = new HashMap<>();
        materialsRepository.getMaterialsByListId(longIdList)
                .stream().forEach(materials -> {materialsMap.put(materials.getId(), materials);});
        return materialsMap;
    }
    private Map<Long, Category> getCategoryByListId(List<Long> longIdList){
        Map<Long, Category> categoryMap = new HashMap<>();
        categoryRepository.getCategoryByListId(longIdList)
                .stream().forEach(category -> categoryMap.put(category.getId(), category));
        return categoryMap;
    }
    private Map<Long, Discounts> getDiscountByListId(List<Long> longIdList){
        Map<Long, Discounts> discountsMap = new HashMap<>();
        discountsRepository.getDiscountsByListId(longIdList)
                .stream().forEach(discounts -> discountsMap.put(discounts.getId(), discounts));
        return discountsMap;
    }
}
