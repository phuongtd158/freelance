package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.OrderDetail;

public interface OderdetailService {
    List<OrderDetail> getOrderDetailsByOrderId(long id);
    List<OrderDetail> getList();

}
