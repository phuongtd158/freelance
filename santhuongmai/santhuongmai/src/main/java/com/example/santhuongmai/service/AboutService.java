package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.About;
import com.example.santhuongmai.model.request.CreateAboutRequest;

public interface AboutService {
	List<About> getListAbout();
	About updateAbout(CreateAboutRequest request);
}
