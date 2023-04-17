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

    public static List<InventoryImportExcelDTO> getDataFromExcel(InputStream inputStream){
        List<InventoryImportExcelDTO> inventoryImportExcelDTOS = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("inventory");

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
                        case 0 -> inventoryImportExcelDTO.setId((int) cell.getNumericCellValue());
                        case 1 -> inventoryImportExcelDTO.setFirstName(cell.getStringCellValue());
                        case 2 -> inventoryImportExcelDTO.setLastName(cell.getStringCellValue());
                        case 3 -> inventoryImportExcelDTO.setCountry(cell.getStringCellValue());
                        case 4 -> inventoryImportExcelDTO.setPhoneNumber(String.valueOf(cell.getNumericCellValue()));
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