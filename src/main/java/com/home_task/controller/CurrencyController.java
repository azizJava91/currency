package com.home_task.controller;
import com.home_task.dto.request.ReqCurrency;
import com.home_task.dto.response.RespCurrency;
import com.home_task.dto.response.Response;
import com.home_task.service.interfaces.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")

public class CurrencyController {
    private final CurrencyService currencyService;


//    http://localhost:8080/currency/compare    GET method

//    {
//            "mail": "aziz@mail.com",
//            "password": "12345",
//            "fromCode":  "USD",
//            "quantity":   3
//    }
    @GetMapping("/compare")
    public Response<RespCurrency> compare(@RequestBody ReqCurrency reqCurrency) {
        return currencyService.compare(reqCurrency);
    }






//    http://localhost:8080/currency/all      GET method

//    {
//            "mail": "aziz@mail.com",
//            "password": "12345"
//    }

    @GetMapping("/all")
    public Response<List<RespCurrency>> getCurrencyList(@RequestBody ReqCurrency reqCurrency) {
        return currencyService.getCurrencyList(reqCurrency);
    }
}

