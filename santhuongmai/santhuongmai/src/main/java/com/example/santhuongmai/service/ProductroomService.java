package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Productroom;
import com.example.santhuongmai.model.request.CreateProductroomRequest;

public interface ProductroomService {
	List<Productroom> getList();  // lấy hết
	List<Productroom> getListroomByUser(long id); // láy theo user
	Productroom createProductroom(CreateProductroomRequest request);//tạo mới
	Productroom updateProductroom(long id, CreateProductroomRequest request); //sửa
	 void deleteProductroom(long id);  //xóa

}
