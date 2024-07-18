
package com.home_task.dto;

import lombok.Data;

@Data
public class ConversionForm {
    private double amount;
    private String sourceCurrency;
    private String targetCurrency;
}
