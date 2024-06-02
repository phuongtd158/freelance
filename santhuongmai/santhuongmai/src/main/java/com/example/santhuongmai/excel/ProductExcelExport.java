package com.example.santhuongmai.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.santhuongmai.entity.Product;

public class ProductExcelExport extends AbstractXlsxView {
	
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
     
        response.setHeader("Content-Disposition", "attachment;filename=Products.xlsx");
        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) model.get("products");
        Sheet sheet = workbook.createSheet("Products");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Price");
        headerRow.createCell(3).setCellValue("Quantity");
        headerRow.createCell(4).setCellValue("QuantityBuy");
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getPrice());
            row.createCell(3).setCellValue(product.getQuantity());
            row.createCell(4).setCellValue(product.getQuantitybuy());
        }
    }
}