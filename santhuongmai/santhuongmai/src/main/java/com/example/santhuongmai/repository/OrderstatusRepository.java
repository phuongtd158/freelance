package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.Orderstatus;

import java.util.List;

@Repository
public interface OrderstatusRepository extends JpaRepository<Orderstatus, Long> {

    @Query(value = "select * from orderstatus o where o.code in :listCode", nativeQuery = true)
    List<Orderstatus> findByListCode(List<String> listCode);
}
