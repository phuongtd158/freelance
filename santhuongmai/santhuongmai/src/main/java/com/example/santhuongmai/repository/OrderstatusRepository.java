package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Orderstatus;

@Repository
public interface OrderstatusRepository extends JpaRepository<Orderstatus,Long> {

}
