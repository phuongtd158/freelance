package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM image WHERE uploaded_by = ?1")
    public List<Image> getListImageOfUser(long userId);

    @Query(
            value = "select i.* from image i join product_image pi on i.id = pi.image_id where pi.product_id = :productId",
            nativeQuery = true
    )
    List<Image> getListImgByProductId(Long productId);
}
