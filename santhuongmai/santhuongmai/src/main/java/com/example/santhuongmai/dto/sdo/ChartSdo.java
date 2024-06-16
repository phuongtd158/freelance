package com.example.santhuongmai.dto.sdo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChartSdo {
    private String field;
    private BigDecimal value;

    private String dateStr;
    private Long productId;
}
