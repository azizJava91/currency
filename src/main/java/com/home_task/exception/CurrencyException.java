package com.home_task.exception;

public class CurrencyException extends RuntimeException {
    private Integer code;

    public CurrencyException(String message) {
        super(message);
    }

    public CurrencyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {

        return code;
    }
}
