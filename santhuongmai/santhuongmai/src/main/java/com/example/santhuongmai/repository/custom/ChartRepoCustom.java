package com.example.santhuongmai.repository.custom;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;

import java.math.BigDecimal;
import java.util.List;

public interface ChartRepoCustom {
    List<ChartSdo> searchRevenue(ChartSdi sdi);
    List<ChartSdo> searchProduct(ChartSdi sdi);
    List<ChartSdo> searchProductDetail(ChartSdi sdi);
    List<ChartSdo> searchProductV2(ChartSdi sdi);
    List<ChartSdo> searchProductV2Detail(ChartSdi sdi);
    Integer getSumQuantityProduct(ChartSdi sdi);
    BigDecimal getSumRevenue(ChartSdi sdi);
    Integer getSumOrderDone(ChartSdi sdi);
}
