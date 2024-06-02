package com.example.santhuongmai.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.santhuongmai.entity.VoucherUser;
import com.example.santhuongmai.model.request.CreateVoucherUserRequest;
import com.example.santhuongmai.repository.VoucherUserRepository;
import com.example.santhuongmai.repository.VoucherRepository;
import com.example.santhuongmai.repository.UserRepository;
import com.example.santhuongmai.service.VoucherUserService;

@Service
public class VoucherUserServiceImpl implements VoucherUserService {

	@Autowired
    private VoucherUserRepository voucherUserRepository;
	@Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;
	@Override
    public VoucherUser createVoucherUser(CreateVoucherUserRequest request) {
        VoucherUser voucherUser = new VoucherUser();
        // Set Voucher
        voucherRepository.findById(request.getVoucherId()).ifPresent(voucherUser::setVoucher);
        // Set User
        userRepository.findById(request.getUserId()).ifPresent(voucherUser::setUser);
        // Set CreateAt
        LocalDateTime createdAt = request.getCreateAt();
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        voucherUser.setCreateAt(createdAt);
        // Save and return
        return voucherUserRepository.save(voucherUser);
    }
}
