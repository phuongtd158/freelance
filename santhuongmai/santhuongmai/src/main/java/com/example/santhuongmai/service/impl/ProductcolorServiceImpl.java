package com.example.santhuongmai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.santhuongmai.entity.Productcolor;
import com.example.santhuongmai.entity.User;
import com.example.santhuongmai.exception.NotFoundException;
import com.example.santhuongmai.model.request.CreateProductcolorRequest;
import com.example.santhuongmai.repository.ProductcolorRepository;
import com.example.santhuongmai.repository.UserRepository;
import com.example.santhuongmai.service.ProductcolorService;
@Service
public class ProductcolorServiceImpl implements ProductcolorService {

    @Autowired
    private ProductcolorRepository productcolorRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    // tất cả
    public List<Productcolor> getList() {
        return productcolorRepository.findAll(Sort.by("id").descending());
    }
    // theo user
    @Override
    public List<Productcolor> getListcolorByUser(long id){
        List<Productcolor> list =productcolorRepository.getListColorByUser(id);
        return list;
    }
    //
    @Override
    public Productcolor createProductcolor(CreateProductcolorRequest request) {
    	Productcolor productcolor = new Productcolor();
    	productcolor.setName(request.getName());
    	User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new NotFoundException("Not Found User"));
    	productcolor.setUser(user);
    	productcolorRepository.save(productcolor);
        return productcolor;
    }
    @Override
    public Productcolor updateProductcolor(long id, CreateProductcolorRequest request) {
        // TODO Auto-generated method stub

    	Productcolor productcolor = productcolorRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
    	productcolor.setName(request.getName());
    	productcolorRepository.save(productcolor);
        return productcolor;
    }

    @Override
    public void deleteProductcolor(long id) {
        // TODO Auto-generated method stub
    	Productcolor productcolor = productcolorRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Foud Tag"));
    	productcolorRepository.delete(productcolor);
    }

}


