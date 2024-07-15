package com.currencytask.currency.controller;

import com.currencytask.currency.request.ReqUser;
import com.currencytask.currency.response.RespUser;
import com.currencytask.currency.response.Response;
import com.currencytask.currency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Response<RespUser> login(@RequestBody ReqUser reqUser) {
        return userService.login(reqUser);
    }

    @GetMapping("/test")
    public String djdj(){
        return"hddh";
    }
}
