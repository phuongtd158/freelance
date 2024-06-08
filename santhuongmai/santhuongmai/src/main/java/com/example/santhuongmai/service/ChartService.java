package com.example.santhuongmai.service;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

public interface ChartService {
    List<ChartSdo> searchRevenue(ChartSdi sdi);
    List<ChartSdo> searchProduct(ChartSdi sdi);
    Integer getSumQuantityProduct();
    BigDecimal getSumRevenue();
    Integer getSumOrderDone();
    Integer getSumProductSell();
    ByteArrayInputStream export(ChartSdi sdi) throws Exception;
}
