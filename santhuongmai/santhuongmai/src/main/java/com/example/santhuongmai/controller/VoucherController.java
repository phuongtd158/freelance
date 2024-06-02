package com.example.santhuongmai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.santhuongmai.entity.Category;
import com.example.santhuongmai.entity.Product;
import com.example.santhuongmai.entity.Voucher;
import com.example.santhuongmai.model.request.CreateVoucherRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.VoucherService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/voucher")
@CrossOrigin(origins = "*",maxAge = 3600)
public class VoucherController {
	@Autowired
    private VoucherService voucherService;
	

    @GetMapping("/")
    @Operation(summary="Lấy ra danh sách voucher")
    public ResponseEntity<List<Voucher>> getList(){
        List<Voucher> list = voucherService.getList();

        return ResponseEntity.ok(list);
    }
    
    @PostMapping("/create")
    @Operation(summary="Tạo mới voucher")
    public ResponseEntity<Voucher> createProduct(@RequestBody CreateVoucherRequest request){
    	Voucher voucher = voucherService.createVoucher(request);
        return ResponseEntity.ok(voucher);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm voucher bằng id và cập nhật voucher đó")
    public ResponseEntity<Voucher> updateProduct(@PathVariable long id,@RequestBody CreateVoucherRequest request){
    	Voucher voucher = voucherService.updateVoucher(id, request);

        return ResponseEntity.ok(voucher);
    }
    @PutMapping("/updatecount/{id}")
    @Operation(summary="Update hạn sử dụng voucher")
    public ResponseEntity<Voucher> updateVouchercount(@PathVariable long id,@RequestBody CreateVoucherRequest request){
    	Voucher voucher = voucherService.updateVouchercount(id, request);

        return ResponseEntity.ok(voucher);
    }
    

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa voucher bằng id")
    public ResponseEntity<?> deleteVoucher(@PathVariable long id){
    	voucherService.deleteVoucher(id);
        return ResponseEntity.ok(new MessageResponse("Product is d  elete"));
    }
    @GetMapping("/uservoucher/{id}")
    @Operation(summary="Lấy ra danh sách voucher theo id user")
    public ResponseEntity<List<Voucher>> getListVoucher(@PathVariable long id){
        List<Voucher> list = voucherService.getListVoucher(id);
        return ResponseEntity.ok(list);
    }
    @PutMapping("/enable/{id}")
    @Operation(summary="Kích hoạt voucher bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
    	voucherService.enableVoucher(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }
}
