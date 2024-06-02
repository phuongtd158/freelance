package com.example.santhuongmai.service.impl;


import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.example.santhuongmai.entity.Productroom;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.exception.NotFoundException;
import com.example.santhuongmai.model.request.CreateProductroomRequest;
import com.example.santhuongmai.repository.ProductroomRepository;
import com.example.santhuongmai.repository.UserRepository;
import com.example.santhuongmai.service.ProductroomService;
@Service
public class ProductroomServiceImpl implements ProductroomService {

    @Autowired
    private ProductroomRepository productroomRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    // tất cả
    public List<Productroom> getList() {
        return productroomRepository.findAll(Sort.by("id").descending());
    }
    // theo user
    @Override
    public List<Productroom> getListroomByUser(long id){
        List<Productroom> list =productroomRepository.getListRoomByUser(id);
        return list;
    }
    //
    @Override
    public Productroom createProductroom(CreateProductroomRequest request) {
    	Productroom productroom = new Productroom();
    	productroom.setName(request.getName());
    	User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new NotFoundException("Not Found User"));
    	productroom.setUser(user);
    	productroomRepository.save(productroom);
        return productroom;
    }
    @Override
    public Productroom updateProductroom(long id, CreateProductroomRequest request) {
        // TODO Auto-generated method stub

    	Productroom productroom = productroomRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
    	productroom.setName(request.getName());
        productroomRepository.save(productroom);
        return productroom;
    }

    @Override
    public void deleteProductroom(long id) {
        // TODO Auto-generated method stub
    	Productroom productroom = productroomRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
    	productroomRepository.delete(productroom);
    }

}
