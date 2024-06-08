package com.example.santhuongmai.dto.sdo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChartSdo {
    @JsonIgnore
    private Integer stt;
    private String field;
    private BigDecimal value;
    private Integer amount;
}
