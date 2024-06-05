package com.example.santhuongmai.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.santhuongmai.service.UploadImageCloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadImageCloudinaryServiceImpl implements UploadImageCloudinaryService {

    private final Cloudinary cloudinary;

    public UploadImageCloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public List<String> upload(MultipartFile[] files) throws IOException {
        List<String> listImageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            listImageUrls.add(save(file));
        }
        return listImageUrls;
    }

    @Override
    public void delete(String publicId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("invalidate", "true");
        cloudinary.uploader().destroy(publicId, params);
    }

    private String save(MultipartFile file) throws IOException {
        Map map = this.cloudinary
                .uploader()
                .upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
        return map.get("secure_url").toString();
    }
}
