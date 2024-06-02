package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting,Long> {
    
}
