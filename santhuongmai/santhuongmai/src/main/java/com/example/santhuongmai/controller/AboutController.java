package com.example.santhuongmai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.santhuongmai.entity.About;
import com.example.santhuongmai.model.request.CreateAboutRequest;
import com.example.santhuongmai.service.AboutService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/about")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AboutController {
	@Autowired
	 private AboutService aboutService;
	 
	 @GetMapping("/")
	 @Operation(summary="Lấy tất cả about")
	 public ResponseEntity<List<About>> getListSetting(){
	 List<About> list = aboutService.getListAbout();
	 return ResponseEntity.ok(list);
	 }
	 @PutMapping("/update")
	    @Operation(summary="Cập nhật about")
	    public ResponseEntity<About> updateAbout(@RequestBody CreateAboutRequest request){
		 About about = aboutService.updateAbout(request);

	        return ResponseEntity.ok(about);
	    }
}
