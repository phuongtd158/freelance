package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Productcolor;
import com.example.santhuongmai.model.request.CreateProductcolorRequest;

public interface ProductcolorService {

	List<Productcolor> getList();  // lấy hết
	List<Productcolor> getListcolorByUser(long id); // láy theo user
	Productcolor createProductcolor(CreateProductcolorRequest request);//tạo mới
	Productcolor updateProductcolor(long id, CreateProductcolorRequest request); //sửa
	 void deleteProductcolor(long id);  //xóa
	
	
}
