package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Productcolor;

@Repository
public interface ProductcolorRepository extends JpaRepository<Productcolor,Long> {
	  @Query(value ="Select * from Productcolor where user_id = :id",nativeQuery = true)
	    List<Productcolor> getListColorByUser(long id);
}