package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.About;
@Repository
public interface AboutRepository extends JpaRepository<About,Long> {
    
}
