package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Tag;
import com.example.santhuongmai.model.request.CreateTagRequest;

public interface TagService {
    
    List<Tag> getListTag();

    Tag createTag(CreateTagRequest request);

    Tag updateTag(long id,CreateTagRequest request);

    void enableTag(long id);

    void deleleTag(long id);

}
