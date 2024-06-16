package com.example.santhuongmai.service.impl;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.dto.sdo.ExcelChartSdo;
import com.example.santhuongmai.repository.custom.ChartRepoCustom;
import com.example.santhuongmai.service.ChartService;
import com.example.santhuongmai.util.DataUtil;
import com.example.santhuongmai.util.DateUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

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
    public List<ChartSdo> searchProductDetail(ChartSdi sdi) {
        return chartRepoCustom.searchProductDetail(sdi);
    }

    @Override
    public List<ChartSdo> searchProductV2(ChartSdi sdi) {
        return chartRepoCustom.searchProductV2(sdi);
    }

    @Override
    public List<ChartSdo> searchProductV2Detail(ChartSdi sdi) {
        return chartRepoCustom.searchProductV2Detail(sdi);
    }

    @Override
    public Integer getSumQuantityProduct(ChartSdi sdi) {
        return chartRepoCustom.getSumQuantityProduct(sdi);
    }

    @Override
    public BigDecimal getSumRevenue(ChartSdi sdi) {
        return chartRepoCustom.getSumRevenue(sdi);
    }

    @Override
    public Integer getSumOrderDone(ChartSdi sdi) {
        return chartRepoCustom.getSumOrderDone(sdi);
    }

    @Override
    public ByteArrayInputStream export(ChartSdi sdi) throws Exception {
        List<ChartSdo> revenues = chartRepoCustom.searchRevenue(sdi);
        List<ChartSdo> countProduct = chartRepoCustom.searchProduct(sdi);
        List<ExcelChartSdo> results = new ArrayList<>();

        for (int i = 0; i < revenues.size(); i++) {
            ChartSdo sdo = revenues.get(i);

            ExcelChartSdo excelChartSdo = new ExcelChartSdo();
            excelChartSdo.setStt(i + 1);
            excelChartSdo.setCol1(sdo.getField());
            excelChartSdo.setCol3(countProduct.get(i).getValue());
            excelChartSdo.setCol4(sdo.getValue());

            ChartSdi sdiNew = new ChartSdi();
            sdiNew.setToDate(sdo.getDateStr());
            sdiNew.setCycleType(sdi.getCycleType());

            List<ChartSdo> productDetails = searchProductDetail(sdiNew);
            StringBuilder mes = new StringBuilder();
            productDetails.forEach(item -> {
                mes.append("- ").append(item.getField()).append(": ").append(item.getValue()).append("\n");
            });

            if (!DataUtil.isNullOrEmpty(mes)) {
                excelChartSdo.setCol2("'".concat(mes.toString()));
            }

            results.add(excelChartSdo);
        }

        try (InputStream is = getClass().getResourceAsStream("/template_chart.xlsx");
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Map<String, Object> beans = new HashMap<>();
            beans.put("createTime", DateUtil.date2ddMMyyyyString(new Date()));
            beans.put("cycleTypeName", sdi.getCycleType().equalsIgnoreCase("YEAR") ? "Năm" :
                    sdi.getCycleType().equalsIgnoreCase("MONTH") ? "Tháng" : "Ngày");
            beans.put("lstData", results);

            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformXLS(is, beans);
            workbook.write(byteArrayOutputStream);

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
