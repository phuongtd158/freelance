package com.example.santhuongmai.repository.custom;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;

import java.math.BigDecimal;
import java.util.List;

public interface ChartRepoCustom {
    List<ChartSdo> searchRevenue(ChartSdi sdi);
    List<ChartSdo> searchProduct(ChartSdi sdi);
    Integer getSumQuantityProduct();
    BigDecimal getSumRevenue();
    Integer getSumOrderDone();
    Integer getSumProductSell();
}
