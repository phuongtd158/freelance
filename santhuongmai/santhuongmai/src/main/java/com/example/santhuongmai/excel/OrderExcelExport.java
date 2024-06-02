package com.example.santhuongmai.excel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import java.util.HashMap;

import com.example.santhuongmai.entity.Order;
import com.example.santhuongmai.entity.OrderDetail;
import com.example.santhuongmai.entity.Orderstatus;

public class OrderExcelExport extends AbstractXlsxView {
	
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// Đặt tên cho file Excel được tạo ra
        response.setHeader("Content-Disposition", "attachment;filename=orders.xlsx");

     // Đọc dữ liệu từ Controller
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) model.get("orders");
        List<OrderDetail> orderDetails = (List<OrderDetail>) model.get("orderDetails");


     // Tạo một sheet mới
        Sheet sheet = workbook.createSheet("Order");
        DecimalFormat currencyFormat = new DecimalFormat("#,### VND");

     // Tạo style cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        
     // Tạo header cho đơn hàng
        Row headerRow = sheet.createRow(0);
        
        
        Row headerRow1 = sheet.createRow(1);
        
     // tiêu đề
     // Tạo một font in đậm
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        // Tạo một style và thiết lập font in đậm
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);

        // Tạo ô và thiết lập giá trị cho ô đó với style in đậm
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Sàn Thương Mại Điện Tử TAZA");
        headerCell.setCellStyle(boldStyle);


        int rowNum = 1; // Bắt đầu từ hàng thứ hai vì hàng đầu tiên đã chứa tiêu đề

        
            Row currentRow = sheet.createRow(rowNum++);
            
            Order id = orders.isEmpty() ? null : orders.get(0);
            if (id != null) {
            	currentRow.createCell(0).setCellValue("Mã Đơn Hàng : " + id.getId());
            }
            Order Address = orders.isEmpty() ? null : orders.get(0);
            if (Address != null) {
            	currentRow.createCell(1).setCellValue("Địa Chỉ: " + id.getAddress());
            }
          
            
            Row currentRow1 = sheet.createRow(rowNum++);
            
            Order Lastname = orders.isEmpty() ? null : orders.get(0);
            if (Lastname != null) {
            	currentRow1.createCell(0).setCellValue("Tên Khách Hàng: " + id.getLastname());
            }
            Order note = orders.isEmpty() ? null : orders.get(0);
            if (note != null) {
            	currentRow1.createCell(1).setCellValue("Ghi Chú: " + note.getNote());
            }
            
            Row currentRow2 = sheet.createRow(rowNum++);            
            Order email = orders.isEmpty() ? null : orders.get(0);
            if (email != null) {
            	currentRow2.createCell(0).setCellValue("Email : " + email.getEmail());
            }
            Order phone = orders.isEmpty() ? null : orders.get(0);
            if (phone != null) {
            	currentRow2.createCell(1).setCellValue("Phone: " + phone.getPhone());
            }
            
            Row currentRow3 = sheet.createRow(rowNum++);            

            Order status = orders.isEmpty() ? null : orders.get(0);
            if (status != null) {
                String paymentMethod = "";
                Orderstatus orderStatusObject = status.getOrderstatus(); // Lấy đối tượng Orderstatus thông qua khóa ngoại
                if (orderStatusObject != null) {
                    long orderStatusId = orderStatusObject.getId(); // Lấy trường id từ đối tượng Orderstatus
                    if (orderStatusId == 0) {
                        paymentMethod = "Chờ Xác Nhận";
                    } else if (orderStatusId == 1) {
                        paymentMethod = "Chờ Lấy Hàng";
                    } else if (orderStatusId == 2) {
                        paymentMethod = "Chờ Giao Hàng";
                    } else if (orderStatusId == 3) {
                        paymentMethod = "Đã Giao";
                    } else if (orderStatusId == 4) {
                        paymentMethod = "Đã Hủy";
                    } else if (orderStatusId == 5) {
                        paymentMethod = "Trả Hàng";
                    } else if (orderStatusId == 6) {
                        paymentMethod = "Lỗi Hệ Thống";
                    } else {
                        paymentMethod = "Lỗi gì rồi";
                    }
                } else {
                    paymentMethod = "Lỗi gì rồi";
                }
                currentRow3.createCell(0).setCellValue("Phương thức thanh toán: " + paymentMethod);
            }     




            
            Order bank = orders.isEmpty() ? null : orders.get(0);
            if (bank != null) {
                String paymentMethod = "";
                if (bank.getBank() == 0) {
                    paymentMethod = "Thanh toán nhận hàng";
                } else if (bank.getBank() == 1) {
                    paymentMethod = "Thanh toán VNPay";
                }
                currentRow3.createCell(1).setCellValue("Phương thức thanh toán: " + paymentMethod);
            }
            
            Row currentRow4 = sheet.createRow(rowNum++);            
            Order giatien = orders.isEmpty() ? null : orders.get(0);
            if (giatien != null) {
            	currentRow4.createCell(1).setCellValue("Thành Tiền : " + currencyFormat.format(giatien.getTotalPrice()));
            }
            Order giamgia = orders.isEmpty() ? null : orders.get(0);
            if (giamgia != null) {
            	currentRow4.createCell(0).setCellValue("Giảm Giá: " + currencyFormat.format(giamgia.getSale()));
            }
            
            
        

            Row currentRow5 = sheet.createRow(rowNum++);    
            Cell ma = currentRow5.createCell(0);
            ma.setCellValue("Mã");
            ma.setCellStyle(boldStyle);
            Cell ma1 = currentRow5.createCell(1);
            ma1.setCellValue("Tên Sản Phẩm");
            ma1.setCellStyle(boldStyle);
            Cell ma2 = currentRow5.createCell(2);
            ma2.setCellValue("Giá");
            ma2.setCellStyle(boldStyle);
            Cell ma3 = currentRow5.createCell(3);
            ma3.setCellValue("Số Lượng");
            ma3.setCellStyle(boldStyle);
           
           
        	

        
        	
        
            int rowNum1 = 7;
            for (OrderDetail orderDetail : orderDetails) {
                Row row = sheet.createRow(rowNum1++);
                row.createCell(0).setCellValue(orderDetail.getId());
                row.createCell(1).setCellValue(orderDetail.getName());
                row.createCell(2).setCellValue(orderDetail.getSoluong());
                row.createCell(3).setCellValue(currencyFormat.format(orderDetail.getPrice()));
                
            }
     // Tự động điều chỉnh kích thước các cột
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
   }
}