package com.example.santhuongmai.service;

import com.example.santhuongmai.entity.VoucherUser;
import com.example.santhuongmai.model.request.CreateVoucherUserRequest;

public interface VoucherUserService {
	VoucherUser createVoucherUser(CreateVoucherUserRequest request);
}
