package com.example.santhuongmai.service.impl;

import java.util.List;

import com.example.santhuongmai.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.santhuongmai.service.RoleService;
import com.example.santhuongmai.entity.Role;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
    private RoleRepository roleRepository;
	
	 @Override
	    public List<Role> getListrole() {
	        // TODO Auto-generated method stub
	        return roleRepository.findAll(Sort.by("id").descending());
	    }
}
