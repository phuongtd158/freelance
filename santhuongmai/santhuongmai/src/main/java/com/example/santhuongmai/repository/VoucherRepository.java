package com.example.santhuongmai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.santhuongmai.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
	  @Query(value = "SELECT b.* FROM `voucher` AS b WHERE b.`enable` = 1 AND b.`count` > 0  AND NOT EXISTS (SELECT * FROM `voucher_user` AS v JOIN `user` AS u ON v.user_id = u.id WHERE v.voucher_id = b.id  AND u.id = :id);", nativeQuery = true)
	    List<Voucher> getListVoucher(long id);
}
