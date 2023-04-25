package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.ProductDetailDTO;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.repo.ProductDetailRepository;
import com.snackman.datnud11.responses.ProductDetailResponse;
import com.snackman.datnud11.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductDetailServiceImp implements ProductDetailService {
    @Autowired
    @Lazy
    private ColorService colorService;


    @Autowired
    @Lazy
    private SizeService sizeService;

    @Autowired
    private ProductDetailRepository repo;

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
    private ZProductService zProductService;

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
            productDetail.setImage(productDetailDTO.getImage());
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
            sizeOption.setImageUrl(productDetail.getImage());
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
                // to do ...
              System.out.println("List size: "+ list.size());
                this.repo.saveAll(swap(list));
            // this.inventoryImportExcelRepo.saveAll(list);
            } catch (IOException e) {
                e.getStackTrace();
                throw new IllegalArgumentException("the file is not valid excel");
            }
        }
    }

    private List<ProductDetail> swap(List<InventoryImportExcelDTO> list){
        List<ProductDetail> productDetailList = new ArrayList<>();
        list.stream().forEach(inventoryImportExcelDTO -> {
            ProductDetail p = new ProductDetail(inventoryImportExcelDTO);
            try {
                String sizeName = sizeService.findById(inventoryImportExcelDTO.getSize()).getSizeName();
                p.setSizeName(sizeName);
                String colorName = colorService.findById(inventoryImportExcelDTO.getColor()).getColorName();
                p.setColorName(colorName);
                int quatity = repo.findById(inventoryImportExcelDTO.getProductDatailId()).get().getQuatity() + inventoryImportExcelDTO.getQuantity();
                p.setQuatity(quatity);
            }catch (Exception e){
              e.printStackTrace();
                throw new RuntimeException("Something wrong in excel file template.");
            }
            productDetailList.add(p);
        });
        return productDetailList;
    }
}
