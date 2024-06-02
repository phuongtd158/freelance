package com.example.santhuongmai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.santhuongmai.entity.Setting;
import com.example.santhuongmai.exception.NotFoundException;
import com.example.santhuongmai.model.request.CreateSettingRequest;
import com.example.santhuongmai.repository.SettingRepository;
import com.example.santhuongmai.service.SettingService;
@Service
public class SettingServiceImpl implements SettingService {
	 @Autowired
	 private SettingRepository settingRepository;
	 @Override
	    public List<Setting> getListSetting() {
	        // TODO Auto-generated method stub
	        return settingRepository.findAll();
	    }

	 @Override
	    public Setting updateSetting(CreateSettingRequest request) {
	        // Lấy tất cả các thiết lập từ cơ sở dữ liệu (trong trường hợp chỉ có một thiết lập)
	        List<Setting> settings = settingRepository.findAll();
	        if (settings.isEmpty()) {
	            throw new NotFoundException("Không tìm thấy thiết lập để cập nhật");
	        }

	        // Lấy thiết lập duy nhất (trong trường hợp chỉ có một thiết lập)
	        Setting setting = settings.get(0);

	        // Cập nhật thông tin mới
	        setting.setGmail(request.getGmail());
	        setting.setPhone(request.getPhone());
	        setting.setLogo(request.getLogo());
	        setting.setFacebook(request.getFacebook());
	        setting.setYoutube(request.getYoutube());
	        setting.setTelegram(request.getTelegram());
	        setting.setInstagram(request.getInstagram());
	        setting.setDiachi(request.getDiachi());

	        // Lưu lại thiết lập đã cập nhật vào cơ sở dữ liệu
	        settingRepository.save(setting);
	        return setting;
	    }
}
