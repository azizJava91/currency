package com.home_task.controller;

import com.home_task.cbar.CBAR;
import com.home_task.dto.ConversionForm;
import com.home_task.dto.request.ReqUser;
import com.home_task.dto.response.RespCurrency;
import com.home_task.entity.CurrencyEntity;
import com.home_task.entity.UserEntity;
import com.home_task.mapper.Mapper;
import com.home_task.repository.CurrencyRepository;
import com.home_task.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping()

public class UIController {
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final Mapper mapper;
    private final CBAR cbar;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute ReqUser reqUser, Model model, HttpServletRequest request) {

        UserEntity userEntity = userRepository.findByMailAndPassword(reqUser.getMail(), reqUser.getPassword());
        if (userEntity == null) {
            model.addAttribute("errorMessage", "İstifadəçi adı və ya mail yanlışdır");
            return "login";
        } else {
            request.getSession().setAttribute("user", userEntity);
            return "redirect:/currencies";
        }
    }

    @GetMapping("/create-account")
    public String showCreateAccountPage(Model model) {
        model.addAttribute("reqUser", new ReqUser());
        return "create-account";
    }

    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute ReqUser reqUser, Model model, HttpServletRequest request) {
        UserEntity user = userRepository.findByMail(reqUser.getMail());
        if (user != null) {
            model.addAttribute("errorMessage", "Bu Mail artiq istifadə olunub");
            return "create-account";
        }
        UserEntity userEntity = UserEntity.builder()
                .mail(reqUser.getMail())
                .password(reqUser.getPassword())
                .mailNotificationPermission(reqUser.getMailNotificationPermission())
                .build();
        userRepository.save(userEntity);
        request.getSession().setAttribute("user", userEntity);
        return "redirect:/currencies";
    }

    @GetMapping("/currencies")
    public String getCurrencies(Model model, HttpServletRequest request) throws IOException {
        UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
        if (userEntity == null) {
            return "login";
        } else {
            List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
            List<RespCurrency> currencies = mapper.fromCurrencyEntityListToRespCurrencyList(currencyEntities);
            if (currencyEntities.isEmpty()) {
                currencyEntities = cbar.updateCurrencyes();
                currencies = mapper.fromCurrencyEntityListToRespCurrencyList(currencyEntities);

            }
            model.addAttribute("currencies", currencies);
            model.addAttribute("conversionForm", new ConversionForm());

            return "currencies";
        }
    }


    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute ConversionForm conversionForm, Model model) {
        List<RespCurrency> currencies = mapper.fromCurrencyEntityListToRespCurrencyList(currencyRepository.findAll());


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

