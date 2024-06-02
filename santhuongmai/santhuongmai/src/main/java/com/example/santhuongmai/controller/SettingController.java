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

import com.example.santhuongmai.entity.Setting;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.model.request.CreateSettingRequest;
import com.example.santhuongmai.service.SettingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/setting")
@CrossOrigin(origins = "*",maxAge = 3600)
public class SettingController {
	 @Autowired
	 private SettingService settingService;
	 
	 @GetMapping("/")
	 @Operation(summary="Lấy tất cả setting")
	 public ResponseEntity<List<Setting>> getListSetting(){
	 List<Setting> list = settingService.getListSetting();
	 return ResponseEntity.ok(list);
	 }
	 @PutMapping("/update")
	    @Operation(summary="Cập nhật setting")
	    public ResponseEntity<Setting> updateSetting(@RequestBody CreateSettingRequest request){
		 Setting setting = settingService.updateSetting(request);

	        return ResponseEntity.ok(setting);
	    }
	 
}
