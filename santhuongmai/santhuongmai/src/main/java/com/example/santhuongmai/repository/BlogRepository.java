package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Blog;
import com.example.santhuongmai.entity.Product;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    
    @Query(value = "Select * from Blog order by id desc limit :limit",nativeQuery = true)
    List<Blog> getListNewest(int limit);
    
    @Query(value ="Select * from Blog where user_id = :id",nativeQuery = true)
    List<Blog> getListBlogByUser(long id);

}
