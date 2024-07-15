package com.currencytask.currency.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class RespStatus {
    private Integer code;
    private String message;
    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "success";

    public static RespStatus getSuccessMessage() {

        return new RespStatus(SUCCESS_CODE, SUCCESS_MESSAGE);
    }
}
