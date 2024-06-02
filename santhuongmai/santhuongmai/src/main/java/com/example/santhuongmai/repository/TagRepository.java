package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    
}
