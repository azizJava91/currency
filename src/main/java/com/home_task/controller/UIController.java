package com.home_task.controller;


import com.home_task.dto.ConversionForm;
import com.home_task.dto.request.ReqCurrency;
import com.home_task.dto.response.RespCurrency;
import com.home_task.service.interfaces.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")

public class UIController {
    private final CurrencyService currencyService;


    @GetMapping("/currencies")
    public String getCurrencies(Model model) {
        ReqCurrency reqCurrency = new ReqCurrency();
        reqCurrency.setMail("aziz@mail.com");
        reqCurrency.setPassword("12345");
        List<RespCurrency> currencies = currencyService.getCurrencyList(reqCurrency).getT();
        model.addAttribute("currencies", currencies);
        model.addAttribute("conversionForm", new ConversionForm());

        return "currencies";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute ConversionForm conversionForm, Model model) {
        ReqCurrency reqCurrency = new ReqCurrency();
        reqCurrency.setMail("aziz@mail.com");
        reqCurrency.setPassword("12345");
        List<RespCurrency> currencies = currencyService.getCurrencyList(reqCurrency).getT();

        double sourceRate = findCurrencyRate(currencies, conversionForm.getSourceCurrency());
        double targetRate = findCurrencyRate(currencies, conversionForm.getTargetCurrency());

        double convertedValue = (conversionForm.getAmount() * targetRate) / sourceRate;
        model.addAttribute("currencies", currencies);
        model.addAttribute("convertedValue", convertedValue);
        model.addAttribute("conversionForm", conversionForm);

        return "currencies";
    }

    private double findCurrencyRate(List<RespCurrency> currencies, String currencyCode) {
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(currencyCode))
                .map(x -> x.getValue().doubleValue())
                .findFirst()
                .orElse(1.0);
    }
}

