package com.example.santhuongmai.dto.sdo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExcelChartSdo {
    private Integer stt;
    private String col1;
    private String col2;
    private BigDecimal col3;
    private BigDecimal col4;
}
