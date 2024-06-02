package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Productroom;

@Repository
public interface ProductroomRepository extends JpaRepository<Productroom,Long> {
	  @Query(value ="Select * from Productroom where user_id = :id",nativeQuery = true)
	    List<Productroom> getListRoomByUser(long id);
}
