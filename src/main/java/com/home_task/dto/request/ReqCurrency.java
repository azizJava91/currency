package com.home_task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqCurrency {
    private String fromCode;
    private BigDecimal quantity;
    private String mail;
    private String password;
}
