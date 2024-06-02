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


import com.example.santhuongmai.entity.Productcolor;
import com.example.santhuongmai.model.request.CreateProductcolorRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.ProductcolorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/productcolor")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductcolorController {
	@Autowired
    private ProductcolorService productcolorService;
	
	@GetMapping("/")
    @Operation(summary="Lấy tất cả danh sách")
    public ResponseEntity<List<Productcolor>> getList(){
        List<Productcolor> list = productcolorService.getList();

        return ResponseEntity.ok(list);

    }
	@GetMapping("/{id}")
    @Operation(summary="Lấy ra danh sách sản phẩm theo id người dùng")
    public ResponseEntity<List<Productcolor>> getListcolorByUser(@PathVariable long id){
        List<Productcolor> list =  productcolorService.getListcolorByUser(id);
        return ResponseEntity.ok(list);
    }
	@PostMapping("/create")
    @Operation(summary="Tạo mới sản phẩm color")
    public ResponseEntity<Productcolor> createProductcolor(@RequestBody CreateProductcolorRequest request){
		Productcolor productcolor = productcolorService.createProductcolor(request);

        return ResponseEntity.ok(productcolor);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm sản phẩm color bằng id và cập nhật sản phẩm đó")
    public ResponseEntity<Productcolor> updateProductcolor(@PathVariable long id,@RequestBody CreateProductcolorRequest request){
    	Productcolor productcolor = productcolorService.updateProductcolor(id, request);

        return ResponseEntity.ok(productcolor);
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa sản phẩm color bằng id")
    public ResponseEntity<?> deleteProductcolor(@PathVariable long id){
    	productcolorService.deleteProductcolor(id);

        return ResponseEntity.ok(new MessageResponse("Product is d  elete"));
    }

}
