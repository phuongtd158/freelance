package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Productsize;

@Repository
public interface ProductsizeRepository extends JpaRepository<Productsize,Long> {
	  @Query(value ="Select * from Productsize where user_id = :id",nativeQuery = true)
	    List<Productsize> getListSizeByUser(long id);
}

