package com.example.santhuongmai.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher_user")
public class VoucherUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDateTime createAt;
//	private long voucherid;
//	private long userid;
	@ManyToOne
    @JoinColumn(name= "user_id")
    private User user;
	
	@ManyToOne
    @JoinColumn(name= "voucher_id")
    private Voucher voucher;
	
}
