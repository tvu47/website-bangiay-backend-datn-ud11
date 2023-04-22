package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.repo.CategoryRepository;
import com.snackman.datnud11.repo.DiscountsRepository;
import com.snackman.datnud11.repo.MaterialsRepository;
import com.snackman.datnud11.responses.ProductsResponse;
import com.snackman.datnud11.services.*;
import com.snackman.datnud11.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private ProductDetailService productDetailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ColorService colorService;

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Override
    public List<ProductsResponse> getAllProductResponses() {
        return formatProductToProductResponse(productService.findAll());
    }

    @Override
    public List<ProductsResponse> getBestSellProducts() {
        return this.formatProductToProductResponse(productService.getBestSellProducts());
    }

    @Override
    public List<ProductsResponse> getNewestProducts() {
        return this.formatProductToProductResponse(productService.getNewestProducts());
    }



    @Override
    public List<ProductsResponse> findByProductId(Long id) throws Exception {
        List<Products> products = new ArrayList<>();
        products.add(this.productService.findById(id));
        List<ProductsResponse> productsResponses = this.formatProductToProductResponse(products);
        return productsResponses;
    }

    public String getPriceProductString(List<ProductDetail> inventories, Long productId) throws Exception{
        Double minPrice = Double.MAX_VALUE;
        Double maxPrice = Double.MIN_VALUE;
        boolean haveInventory = false;
        for(ProductDetail productDetail : inventories){
            if(productDetail.getProductId() == productId){
                haveInventory = true;
                if(productDetail.getPrice() > maxPrice){
                    maxPrice = productDetail.getPrice();
                } else if(productDetail.getPrice() < minPrice){
                    minPrice = productDetail.getPrice();
                }
            }
        }
        if(!haveInventory){
            throw new Exception();
        }
        return NumberUtil.formatNumberVN(minPrice) + "đ - " + NumberUtil.formatNumberVN(maxPrice) + "đ";
    }

    public List<ProductsResponse> formatProductToProductResponse(List<Products> productsList){
        List<ProductsResponse> productsResponsesList = new ArrayList<>();
        // get id list
        Map<String, List<Long>> idList = getListIds(productsList);
        List<ProductDetail> productDetailList = this.productDetailService.findAll();

        Map<Long, Materials> materialsMap = getMaterialByListId(idList.get("material"));
        Map<Long, Category> categoryMap= getCategoryByListId(idList.get("category"));
        List<Category> categories = this.categoryService.findAll();

        productsList.stream().forEach(products -> {
            if(!products.getStatus()){
                return;
            }
            ProductDetail inv = null;
            try {
                inv = this.productDetailService.findByProductId(productDetailList, products.getId()).get(0);
            }catch (Exception e){
                return;
            }
            ProductsResponse productsResponse = new ProductsResponse(products);
            productsResponse.setImages(inv.getImage());
            productsResponse.setCategory(categoryMap.get(products.getCategoryId()));
            productsResponse.setMaterials(materialsMap.get(products.getMaterialId()));
            if(productsResponse.getCategory() == null){
                try {
                    productsResponse.setCategory(this.categoryService.findById(categories, products.getCategoryId()));
                } catch (Exception e) {
                }
            }
            try {
                productsResponse.setPrice(this.getPriceProductString(productDetailList, products.getId()));
                productsResponsesList.add(productsResponse);
            } catch (Exception e) {
            }
        });
        return productsResponsesList;
    }

    @Override
    public List<ProductsResponse> getSameProducts(Long categoryId, Long productId) {
        return this.formatProductToProductResponse(this.productService.findBySameCategory(categoryId, productId));
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
