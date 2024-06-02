package com.example.santhuongmai.controller;

import com.example.santhuongmai.excel.OrderDetailExcelExport;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse; // Thêm import này vào

import com.example.santhuongmai.entity.Order;
import com.example.santhuongmai.entity.OrderDetail;
import com.example.santhuongmai.excel.OrderDetailExcelExport;
import com.example.santhuongmai.service.OderdetailService;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api/orderdetail")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderDetailController {
	@Autowired
    private OderdetailService oderdetailService;
	
	@GetMapping("/")
    @Operation(summary="Lấy ra danh sách đặt hàng")
    public ResponseEntity<List<OrderDetail>> getList(){
        List<OrderDetail> list = oderdetailService.getList();

        return ResponseEntity.ok(list);
    }
	
	
	@GetMapping("/chitiet/{id}")
    @Operation(summary="Lấy ra danh sách đơn hàng bằng id của đơn hàng")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable long id){
        List<OrderDetail> list = oderdetailService.getOrderDetailsByOrderId(id);
        return ResponseEntity.ok(list);
    }

	@GetMapping("/exportexcel")
	@Operation(summary="Xuất excel chi tiết đơn hàng")
    public ModelAndView exportToExcel(HttpServletResponse response) {      
        List<OrderDetail> orderDetails = oderdetailService.getList();
        ModelAndView modelAndView = new ModelAndView(new OrderDetailExcelExport(), "orderDetails", orderDetails);
        return modelAndView;
    }
	
}
