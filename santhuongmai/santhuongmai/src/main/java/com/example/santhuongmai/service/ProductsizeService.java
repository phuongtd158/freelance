package com.example.santhuongmai.service;
import java.util.List;


import com.example.santhuongmai.entity.Productsize;
import com.example.santhuongmai.model.request.CreateProductsizeRequest;
public interface ProductsizeService {
	List<Productsize> getList();  // lấy hết
	List<Productsize> getListsizeByUser(long id); // láy theo user
	Productsize createProductsize(CreateProductsizeRequest request);//tạo mới
	Productsize updateProductsize(long id, CreateProductsizeRequest request); //sửa
	 void deleteProductsize(long id);  //xóa

}
