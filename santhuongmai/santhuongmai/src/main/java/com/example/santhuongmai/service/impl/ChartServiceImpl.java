package com.example.santhuongmai.service.impl;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.repository.custom.ChartRepoCustom;
import com.example.santhuongmai.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {
    private final ChartRepoCustom chartRepoCustom;

    @Override
    public List<ChartSdo> searchRevenue(ChartSdi sdi) {
        return chartRepoCustom.searchRevenue(sdi);
    }

    @Override
    public List<ChartSdo> searchProduct(ChartSdi sdi) {
        return chartRepoCustom.searchProduct(sdi);
    }

    @Override
    public Integer getSumQuantityProduct() {
        return chartRepoCustom.getSumQuantityProduct();
    }

    @Override
    public BigDecimal getSumRevenue() {
        return chartRepoCustom.getSumRevenue();
    }

    @Override
    public Integer getSumOrderDone() {
        return chartRepoCustom.getSumOrderDone();
    }

    @Override
    public Integer getSumProductSell() {
        return chartRepoCustom.getSumProductSell();
    }
}
