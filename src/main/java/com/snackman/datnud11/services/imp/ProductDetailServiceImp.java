package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.ProductDetailDTO;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.repo.InventoryRepository;
import com.snackman.datnud11.responses.ProductDetailResponse;
import com.snackman.datnud11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductDetailServiceImp implements ProductDetailService {
    @Autowired
    @Lazy
    private ColorService colorService;


    @Autowired
    @Lazy
    private SizeService sizeService;

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    @Lazy
    private MaterialService materialService;

    @Autowired
    @Lazy
    private ProductService productService;
    @Autowired
    private InventoryRepository repo;

    @Autowired
    private ZProductService zProductService;


    @Autowired
    private ProductDetailService productDetailService;

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return this.repo.save(productDetail);
    }

    @Override
    public ProductDetail findBySku(String sku) {
        return this.repo.findBySku(sku);
    }

    @Override
    public List<ProductDetail> findByProductId(Long id) {
        return this.repo.findByProductId(id);
    }

    @Override
    public List<ProductDetail> findAll() {
        return this.repo.findAll();
    }

    @Override
    public List<ProductDetail> findByProductId(List<ProductDetail> productDetailList, Long id) {
        List<ProductDetail> list = new ArrayList<>();
        for(ProductDetail productDetail : productDetailList){
            if(productDetail.getProductId() == id){
                list.add(productDetail);
            }
        }
        return list;
    }

    @Override
    public ProductDetailResponse getProductById(Long id) throws Exception {
        List<ProductDetail> productDetail = this.repo.findByProductId(id);
        return this.convertInventoryToInventoryResponse(productDetail);
    }

    @Override
    public boolean validProductsOrder(List<PaymentDTO.ProductOrder> productOrders) {
        for(PaymentDTO.ProductOrder productOrder: productOrders){
            if(productOrder.getQuantity() < 1){
                return false;
            }
            ProductDetail productDetail = this.findBySku(productOrder.getSku());
            if(productDetail == null || productDetail.getQuatity() < productOrder.getQuantity()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ProductDetail save(ProductDetailDTO productDetailDTO) throws Exception {
        Colors colors = null;
        Size size = null;
        try {
            colors = this.colorService.findByColorName(productDetailDTO.getColor());
        } catch (Exception e){
            colors = new Colors();
            colors.setColorName(productDetailDTO.getColor());
            colors.setStatus(true);
            this.colorService.save(colors);
        }
        try {
            size = this.sizeService.findBySizeName(productDetailDTO.getSize());
        }catch (Exception e){
            size = new Size();
            size.setSizeName(productDetailDTO.getSize());
            size.setActiveStatus(true);
            this.sizeService.save(size);
        }
        String sku = "P" + productDetailDTO.getProductId() + "C" + colors.getId() + "S" + size.getId();
        ProductDetail productDetail = this.findBySku(sku);
        if(productDetail != null){
            productDetail.setQuatity(productDetail.getQuatity() + productDetailDTO.getQuantity());
        } else {
            productDetail = new ProductDetail();
            productDetail.setSku(sku);
            productDetail.setProductId(productDetailDTO.getProductId());
            productDetail.setColor(colors.getId());
            productDetail.setSize(size.getId());
            productDetail.setColorName(colors.getColorName());
            productDetail.setSizeName(size.getSizeName());
            productDetail.setQuatity(productDetailDTO.getQuantity());
            productDetail.setPrice(productDetailDTO.getPrice());
            productDetail.setImportTime(new Date());
            productDetail.setStatus(true);
        }
        this.save(productDetail);
        return productDetail;
    }

    private ProductDetailResponse convertInventoryToInventoryResponse(List<ProductDetail> list) throws Exception {
        ProductDetailResponse response = new ProductDetailResponse();
        response.setProducts(this.zProductService.findByProductId(list.get(0).getProductId()).get(0));
        List<Colors> colorsList = this.colorService.findAll();
        List<Size> sizeList = this.sizeService.findAll();
        for(ProductDetail productDetail : list){
            ProductDetailResponse.ColorOption colorOptionTemp = null;
            for(ProductDetailResponse.ColorOption colorOption : response.getColorOptions()){
                if(colorOption.getColors().getId() == productDetail.getColor()){
                    colorOptionTemp = colorOption;
                    break;
                }
            }
            if(colorOptionTemp == null){
                colorOptionTemp = new ProductDetailResponse.ColorOption();
                colorOptionTemp.setColors(this.colorService.findById(colorsList, productDetail.getColor()));
                response.getColorOptions().add(colorOptionTemp);
            }
            ProductDetailResponse.SizeOption sizeOption = new ProductDetailResponse.SizeOption();
            sizeOption.setSize(this.sizeService.findById( sizeList, productDetail.getSize()));
            sizeOption.setPrice(productDetail.getPrice());
            sizeOption.setQuantity(productDetail.getQuatity());
            colorOptionTemp.getSizeOptions().add(sizeOption);
        }
        return response;
    }

    @Override
    public void saveInventoryToDatabase(MultipartFile multipartFile){
        if (ExcelUploadService.isValidExcelFile(multipartFile)){
            try {
                // list data from excel file
                List<InventoryImportExcelDTO> list = ExcelUploadService.getDataFromExcel(multipartFile.getInputStream());
                List<Products> products = this.productService.findAll();
                List<Category> categories = this.categoryService.findAll();
                List<Materials> materials = this.materialService.findAll();
                List<Colors> colors = this.colorService.findAll();
                List<Size> sizes = this.sizeService.findAll();
                List<ProductDetail> productDetails = this.findAll();
                for(InventoryImportExcelDTO data : list){
                    Products prod = null;
                    Category cate = null;
                    Materials mate = null;
                    Colors color = null;
                    Size size = null;
                    ProductDetail productDetail = null;
                    try {
                        color = this.colorService.findByName(colors, data.getColor());
                    } catch (Exception e){
                    }
                    try {
                        size = this.sizeService.findByName(sizes, data.getSize());
                    } catch (Exception e){
                    }
                    try {
                        prod = this.productService.findByNameInList(products, data.getNameProduct());
                    } catch (Exception e){
                    }
                    try {
                        prod = this.productService.findByNameInList(products, data.getNameProduct());
                    } catch (Exception e){
                    }
                    try {
                        mate = this.materialService.findByName(materials, data.getMaterial());
                    } catch (Exception e){
                    }
                    try {
                        cate = this.categoryService.findByName(categories, data.getCategory());
                    } catch (Exception e){
                    }
                    if(color == null){
                        color = new Colors();
                        color.setStatus(true);
                        color.setColorName(data.getColor());
                        this.colorService.save(color);
                        colors.add(color);
                    }
                    if(size == null){
                        size = new Size();
                        size.setActiveStatus(true);
                        size.setSizeName(data.getSize());
                        this.sizeService.save(size);
                        sizes.add(size);
                    }
                    if(mate == null){
                        mate = new Materials();
                        mate.setStatus(true);
                        mate.setImportDate(new Date());
                        mate.setMaterialName(data.getMaterial());
                        mate.setMaterialLocation("");
                        this.materialService.save(mate);
                        materials.add(mate);
                    }
                    if(cate == null){
                        cate = new Category();
                        cate.setStatus(true);
                        cate.setCategoryName(data.getCategory());
                        this.categoryService.save(cate);
                        categories.add(cate);
                    }
                    if(prod == null){
                        prod = new Products();
                        prod.setManufactureAddress(data.getManufacture());
                        prod.setStatus(true);
                        prod.setContent(data.getContents());
                        prod.setMaterialId(mate.getId());
                        prod.setCategoryId(cate.getId());
                        prod.setProductName(data.getNameProduct());
                        this.productService.save(prod);
                        products.add(prod);
                    }
                    try {
                        productDetail = this.productDetailService.findBySku("P" + prod.getId() + "C" + color.getId() + "S" + size.getId());
                    } catch (Exception e){
                    }
                    if(productDetail == null){
                        productDetail = new ProductDetail();
                        productDetail.setSku("P" + prod.getId() + "C" + color.getId() + "S" + size.getId());
                        productDetail.setProductId(prod.getId());
                        productDetail.setQuatity(data.getQuantity());
                        productDetail.setPrice(data.getPrice());
                        productDetail.setImportTime(new Date());
                        productDetail.setStatus(true);
                        productDetail.setColor(color.getId());
                        productDetail.setSize(size.getId());
                        productDetail.setColorName(color.getColorName());
                        productDetail.setSizeName(size.getSizeName());
                        productDetail.setImage(data.getImage());
                    } else {
                        productDetail.setQuatity(productDetail.getQuatity() + data.getQuantity());
                        productDetail.setImportTime(new Date());
                    }
                    this.save(productDetail);

                }
            } catch (IOException e) {
                e.getStackTrace();
                throw new IllegalArgumentException("the file is not valid excel");
            }
        }
    }
}
