package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.VoucherUser;

@Repository
public interface VoucherUserRepository  extends JpaRepository<VoucherUser,Long> {

}
