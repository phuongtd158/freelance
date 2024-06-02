package com.example.santhuongmai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.model.request.ChangePasswordRequest;
import com.example.santhuongmai.model.request.CreateBlogRequest;
import com.example.santhuongmai.model.request.CreateUserRequest;
import com.example.santhuongmai.model.request.UpdateProfileRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/alluser")
    @Operation(summary="Lấy tất cả danh sách user")
    public ResponseEntity<List<User>> getListuser(){
        List<User> list = userService.getListuser();

        return ResponseEntity.ok(list);

    }
    @GetMapping("/{id}")
    @Operation(summary="Lấy ra user bằng ID")
    public ResponseEntity<User> getUser(@PathVariable long id){
        
    	User user =userService.getUser(id);
        return ResponseEntity.ok(user);

    }
    @GetMapping("/")
    @Operation(summary="Lấy ra user bằng username")
    public ResponseEntity<User> getuser(@RequestParam("username") String username){
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update/{id}")
    @Operation(summary="Tìm user bằng id và cập nhật user đó")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody CreateUserRequest request){

    	User user = userService.updatesUser(id, request);

        return ResponseEntity.ok(user);

    }
    @PutMapping("/update")
    @Operation(summary="Cập nhật user")
    public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileRequest request){
        User user = userService.updateUser(request);

        return ResponseEntity.ok(user);
    }
    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
        @RequestParam String otp) {
      return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
      return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa user bằng Id")
    public ResponseEntity<?> delete(@PathVariable long id){
       userService.deleleUser(id);
        return ResponseEntity.ok(new MessageResponse("Delete success"));
    }

     @PutMapping("/password")
     public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
         userService.changePassword(request);
         return ResponseEntity.ok(new MessageResponse("Change Password Success!"));
     }
}
