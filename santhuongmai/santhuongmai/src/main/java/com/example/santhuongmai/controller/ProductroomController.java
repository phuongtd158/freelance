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

import com.example.santhuongmai.entity.Productroom;
import com.example.santhuongmai.model.request.CreateProductroomRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.ProductroomService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/productroom")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductroomController {
	@Autowired
    private ProductroomService productroomService;
	
	@GetMapping("/")
    @Operation(summary="Lấy tất cả danh sách")
    public ResponseEntity<List<Productroom>> getList(){
        List<Productroom> list = productroomService.getList();

        return ResponseEntity.ok(list);

    }
	@GetMapping("/{id}")
    @Operation(summary="Lấy ra danh sách sản phẩm theo id người dùng")
    public ResponseEntity<List<Productroom>> getListroomByUser(@PathVariable long id){
        List<Productroom> list =  productroomService.getListroomByUser(id);
        return ResponseEntity.ok(list);
    }
	@PostMapping("/create")
    @Operation(summary="Tạo mới sản phẩm color")
    public ResponseEntity<Productroom> createProductroom(@RequestBody CreateProductroomRequest request){
		Productroom productroom = productroomService.createProductroom(request);

        return ResponseEntity.ok(productroom);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm sản phẩm color bằng id và cập nhật sản phẩm đó")
    public ResponseEntity<Productroom> updateProductroom(@PathVariable long id,@RequestBody CreateProductroomRequest request){
    	Productroom productroom = productroomService.updateProductroom(id, request);

        return ResponseEntity.ok(productroom);
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa sản phẩm color bằng id")
    public ResponseEntity<?> deleteProductroom(@PathVariable long id){
    	productroomService.deleteProductroom(id);

        return ResponseEntity.ok(new MessageResponse("Product is d  elete"));
    }

}
