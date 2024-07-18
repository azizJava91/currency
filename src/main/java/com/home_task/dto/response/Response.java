package com.home_task.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response<T> {
    @JsonProperty(value = "respons")
    private T t;

    private RespStatus respons_Status;

}
