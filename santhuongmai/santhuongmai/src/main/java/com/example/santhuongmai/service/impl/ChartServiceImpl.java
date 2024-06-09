package com.example.santhuongmai.service.impl;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.repository.custom.ChartRepoCustom;
import com.example.santhuongmai.service.ChartService;
import com.example.santhuongmai.util.DateUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ByteArrayInputStream export(ChartSdi sdi) throws Exception {
        List<ChartSdo> results = chartRepoCustom.searchRevenue(sdi);

        try (InputStream is = getClass().getResourceAsStream("/template_chart.xlsx");
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            for (int i = 0; i < results.size(); i++) {
                results.get(i).setStt(i + 1);
            }

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
