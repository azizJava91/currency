package com.home_task.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCurrency {
    private BigDecimal nominal;
    private String code;
    private String name;
    private BigDecimal value;
}
