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


import com.example.santhuongmai.entity.Contact;
import com.example.santhuongmai.model.request.CreateContactRequest;
import com.example.santhuongmai.model.response.MessageResponse;
import com.example.santhuongmai.service.ContactService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ContactController {
	@Autowired
    private ContactService contactService;
	@GetMapping("/")
    @Operation(summary="Lấy tất cả danh sách Contact")
    public ResponseEntity<List<Contact>> getList(){
        List<Contact> list = contactService.getList();
        return ResponseEntity.ok(list);
    }
	@GetMapping("/{id}")
    @Operation(summary="Lấy ra contact bằng ID")
    public ResponseEntity<Contact> getContact(@PathVariable long id){        
		Contact contact =contactService.getContact(id);
        return ResponseEntity.ok(contact);

    }
	@PostMapping("/create")
    @Operation(summary="Tạo mới contact")
    public ResponseEntity<Contact> createContact(@RequestBody CreateContactRequest request){

		Contact contact = contactService.createContact(request);

        return ResponseEntity.ok(contact);

    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm blog bằng id và cập nhật contact đó")
    public ResponseEntity<Contact> update(@PathVariable long id, @RequestBody CreateContactRequest request){

    	Contact contact = contactService.updateContact(id, request);

        return ResponseEntity.ok(contact);

    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa contact bằng Id")
    public ResponseEntity<?> delete(@PathVariable long id){
    	contactService.deleteContact(id);
        return ResponseEntity.ok(new MessageResponse("Delete success"));
    }
    @PutMapping("/enable/{id}")
    @Operation(summary="Xác nhận contact bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
    	contactService.enableContact(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }
}
