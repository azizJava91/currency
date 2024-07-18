package com.home_task.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReqCurrency {
    private String fromCode;
    private BigDecimal quantity;
    private String mail;
    private String password;
}
