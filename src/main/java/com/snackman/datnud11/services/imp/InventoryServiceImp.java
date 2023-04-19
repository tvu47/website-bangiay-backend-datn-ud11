package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.InventoryDTO;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.entity.*;
import com.snackman.datnud11.repo.InventoryRepository;
import com.snackman.datnud11.responses.InventoryResponse;
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
public class InventoryServiceImp implements InventoryService {

    @Autowired
    private InventoryRepository repo;

    @Autowired
    private ZProductService zProductService;

    @Autowired
    @Lazy
    private ColorService colorService;


    @Autowired
    @Lazy
    private SizeService sizeService;


    @Autowired
    @Lazy
    private InventoryService inventoryService;

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    @Lazy
    private MaterialService materialService;

    @Autowired
    @Lazy
    private ProductService productService;

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
    public List<Inventory> findAll() {
        return this.repo.findAll();
    }

    @Override
    public List<Inventory> findByProductId(List<Inventory> inventoryList, Long id) {
        List<Inventory> list = new ArrayList<>();
        for(Inventory inventory : inventoryList){
            if(inventory.getProductId() == id){
                list.add(inventory);
            }
        }
        return list;
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

    @Override
    public Inventory save(InventoryDTO inventoryDTO) throws Exception {
        Colors colors = null;
        Size size = null;
        try {
            colors = this.colorService.findByColorName(inventoryDTO.getColor());
        } catch (Exception e){
            colors = new Colors();
            colors.setColorName(inventoryDTO.getColor());
            colors.setStatus(true);
            this.colorService.save(colors);
        }
        try {
            size = this.sizeService.findBySizeName(inventoryDTO.getSize());
        }catch (Exception e){
            size = new Size();
            size.setSizeName(inventoryDTO.getSize());
            size.setActiveStatus(true);
            this.sizeService.save(size);
        }
        String sku = "P" + inventoryDTO.getProductId() + "C" + colors.getId() + "S" + size.getId();
        Inventory inventory = this.findBySku(sku);
        if(inventory != null){
            inventory.setQuatity(inventory.getQuatity() + inventoryDTO.getQuantity());
        } else {
            inventory = new Inventory();
            inventory.setSku(sku);
            inventory.setProductId(inventoryDTO.getProductId());
            inventory.setColor(colors.getId());
            inventory.setSize(size.getId());
            inventory.setColorName(colors.getColorName());
            inventory.setSizeName(size.getSizeName());
            inventory.setQuatity(inventoryDTO.getQuantity());
            inventory.setPrice(inventoryDTO.getPrice());
            inventory.setImportTime(new Date());
            inventory.setStatus(true);
        }
        this.save(inventory);
        return inventory;
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
            sizeOption.setImageUrl(inventory.getImage());
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
                List<Inventory> inventories = this.inventoryService.findAll();
                for(InventoryImportExcelDTO data : list){
                    Products prod = null;
                    Category cate = null;
                    Materials mate = null;
                    Colors color = null;
                    Size size = null;
                    Inventory inventory = null;
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
                        inventory = this.inventoryService.findBySku("P" + prod.getId() + "C" + color.getId() + "S" + size.getId());
                    } catch (Exception e){
                    }
                    if(inventory == null){
                        inventory = new Inventory();
                        inventory.setSku("P" + prod.getId() + "C" + color.getId() + "S" + size.getId());
                        inventory.setProductId(prod.getId());
                        inventory.setQuatity(data.getQuantity());
                        inventory.setPrice(data.getPrice());
                        inventory.setImportTime(new Date());
                        inventory.setStatus(true);
                        inventory.setColor(color.getId());
                        inventory.setSize(size.getId());
                        inventory.setColorName(color.getColorName());
                        inventory.setSizeName(size.getSizeName());
                        inventory.setImage(data.getImage());
                    } else {
                        inventory.setQuatity(inventory.getQuatity() + data.getQuantity());
                        inventory.setImportTime(new Date());
                    }
                    this.inventoryService.save(inventory);

                }
                // to do ...
//                this.inventoryImportExcelRepo.saveAll(list);
            } catch (IOException e) {
                e.getStackTrace();
                throw new IllegalArgumentException("the file is not valid excel");
            }
        }
    }
}
