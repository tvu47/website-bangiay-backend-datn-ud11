package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.InventoryImportExcelDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }


    /*
    * lấy data từ excel
    * */
    public static List<InventoryImportExcelDTO> getDataFromExcel(InputStream inputStream){
        List<InventoryImportExcelDTO> inventoryImportExcelDTOS = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("ProductDetail");

            int rowIndex = 0;
            for (Row row: sheet){
                if (rowIndex==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                InventoryImportExcelDTO inventoryImportExcelDTO = new InventoryImportExcelDTO();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> inventoryImportExcelDTO.setProductDatailId((long) cell.getNumericCellValue());
                        case 1 -> inventoryImportExcelDTO.setSku(cell.getStringCellValue());
                        case 2 -> inventoryImportExcelDTO.setProductId((long) cell.getNumericCellValue());
                        case 3 -> inventoryImportExcelDTO.setColor((long) cell.getNumericCellValue());
                        case 4 -> inventoryImportExcelDTO.setSize((long) cell.getNumericCellValue());
                        case 5 -> inventoryImportExcelDTO.setQuantity((int) cell.getNumericCellValue());
                        case 6 -> inventoryImportExcelDTO.setPrice(cell.getNumericCellValue());
                        case 7 -> inventoryImportExcelDTO.setImage(cell.getStringCellValue());
                        default -> {}
                    }
                    cellIndex++;
                }
                inventoryImportExcelDTOS.add(inventoryImportExcelDTO);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return inventoryImportExcelDTOS;
    }
}
