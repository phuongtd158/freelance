package com.example.santhuongmai.excel;
import com.example.santhuongmai.entity.OrderDetail;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
public class OrderDetailExcelExport extends AbstractXlsxView {
	
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Define the Excel file name to be exported
        response.setHeader("Content-Disposition", "attachment;filename=OrderDetails.xlsx");

        // Read data provided by the controller
        @SuppressWarnings("unchecked")
        List<OrderDetail> orderDetails = (List<OrderDetail>) model.get("orderDetails");

        // Create a new sheet
        Sheet sheet = workbook.createSheet("OrderDetails");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Price");
        headerRow.createCell(3).setCellValue("Quantity");
        headerRow.createCell(4).setCellValue("Subtotal");

        // Create subsequent rows from the List<OrderDetail>
        int rowNum = 1;
        for (OrderDetail orderDetail : orderDetails) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(orderDetail.getId());
            row.createCell(1).setCellValue(orderDetail.getName());
            row.createCell(2).setCellValue(orderDetail.getPrice());
            row.createCell(3).setCellValue(orderDetail.getSoluong());
            row.createCell(4).setCellValue(orderDetail.getSubTotal());
        }
    }
}