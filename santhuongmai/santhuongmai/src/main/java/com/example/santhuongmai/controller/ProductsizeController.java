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

import com.example.santhuongmai.entity.Productsize;
import com.example.santhuongmai.model.request.CreateProductsizeRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.ProductsizeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/productsize")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductsizeController {
	@Autowired
    private ProductsizeService productsizeService;
	
	@GetMapping("/")
    @Operation(summary="Lấy tất cả danh sách")
    public ResponseEntity<List<Productsize>> getList(){
        List<Productsize> list = productsizeService.getList();

        return ResponseEntity.ok(list);

    }
	@GetMapping("/{id}")
    @Operation(summary="Lấy ra danh sách sản phẩm theo id người dùng")
    public ResponseEntity<List<Productsize>> getListsizeByUser(@PathVariable long id){
        List<Productsize> list =  productsizeService.getListsizeByUser(id);
        return ResponseEntity.ok(list);
    }
	@PostMapping("/create")
    @Operation(summary="Tạo mới sản phẩm color")
    public ResponseEntity<Productsize> createProductsize(@RequestBody CreateProductsizeRequest request){
		Productsize productsize = productsizeService.createProductsize(request);

        return ResponseEntity.ok(productsize);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm sản phẩm color bằng id và cập nhật sản phẩm đó")
    public ResponseEntity<Productsize> updateProductsize(@PathVariable long id,@RequestBody CreateProductsizeRequest request){
    	Productsize productsize = productsizeService.updateProductsize(id, request);

        return ResponseEntity.ok(productsize);
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa sản phẩm color bằng id")
    public ResponseEntity<?> deleteProductsize(@PathVariable long id){
    	productsizeService.deleteProductsize(id);

        return ResponseEntity.ok(new MessageResponse("Product is d  elete"));
    }

}
