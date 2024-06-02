package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Setting;
import com.example.santhuongmai.model.request.CreateSettingRequest;

public interface SettingService {
	List<Setting> getListSetting();
	Setting updateSetting(CreateSettingRequest request);
	
}
