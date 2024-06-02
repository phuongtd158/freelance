package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.Product;
import com.example.santhuongmai.model.request.CreateBlogRequest;

public interface BlogService {
    
    List<Blog> getList();

    List<Blog> getListNewest(int limit);

    Blog getBlog(long id);

    Blog createBlog(CreateBlogRequest request);

    Blog updateBlog(long id,CreateBlogRequest request);

    void deleteBlog(long id);
    List<Blog> getListBlogByUser(long id);

}
