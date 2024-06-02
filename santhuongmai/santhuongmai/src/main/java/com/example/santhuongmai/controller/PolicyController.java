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

import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.Policy;
import com.example.santhuongmai.model.request.CreatePolicyRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.PolicyService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/policy")
@CrossOrigin(origins = "*",maxAge = 3600)
public class PolicyController {
	@Autowired
	 private PolicyService policyService;
	
	 	@GetMapping("/")
	    @Operation(summary="Lấy tất cả danh sách Policy")
	    public ResponseEntity<List<Policy>> getList(){
	        List<Policy> list = policyService.getList();
	        return ResponseEntity.ok(list);
	    }
	 
	    @GetMapping("/kieu/{id}")
	    @Operation(summary="Lấy ra danh sách theo kieu")
	    public ResponseEntity<List<Policy>> getListPolicyBykieu(@PathVariable long id){
	        List<Policy> list =  policyService.getListPolicyBykieu(id);
	        return ResponseEntity.ok(list);
	    }
	    
	    @GetMapping("/{id}")
	    @Operation(summary="Lấy ra policy bằng ID")
	    public ResponseEntity<Policy> getPolicy(@PathVariable long id){	        
	    	Policy policy =policyService.getPolicy(id);
	        return ResponseEntity.ok(policy);
	    }
	    @PostMapping("/create")
	    @Operation(summary="Tạo mới policy")
	    public ResponseEntity<Policy> create(@RequestBody CreatePolicyRequest request){
	    	Policy policy = policyService.createPolicy(request);
	        return ResponseEntity.ok(policy);	    
	    }

	    @PutMapping("/update/{id}")
	    @Operation(summary="Tìm policy bằng id và cập nhật policy đó")
	    public ResponseEntity<Policy> update(@PathVariable long id, @RequestBody CreatePolicyRequest request){
	    	Policy policy = policyService.updatePolicy(id, request);
	        return ResponseEntity.ok(policy);
	    }

	    @DeleteMapping("/delete/{id}")
	    @Operation(summary="Xóa policy bằng Id")
	    public ResponseEntity<?> delete(@PathVariable long id){
	    	policyService.deletePolicy(id);
	        return ResponseEntity.ok(new MessageResponse("Delete success"));
	    }
	    @PutMapping("/enable/{id}")
	    @Operation(summary="Kích hoạt Policy bằng id")
	    public ResponseEntity<?> enabled(@PathVariable long id){
	    	policyService.enablePolicy(id);
	        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
	    }
}
