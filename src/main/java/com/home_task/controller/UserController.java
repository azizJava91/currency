package com.home_task.controller;

import com.home_task.dto.request.ReqUser;
import com.home_task.dto.response.RespUser;
import com.home_task.dto.response.Response;
import com.home_task.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

//       http://localhost:8080/user/register        POST method

    //
//        {
//            "mail": "aziz@mail.com",
//            "password": "12345",
//            "mailNotificationPermission": "no"
//    }
    @PostMapping("/register")
    public Response<RespUser> register(@RequestBody ReqUser reqUser) {
        return userService.register(reqUser);
    }

    @PostMapping("/login")
    public Response<RespUser> login(@RequestBody ReqUser reqUser) {
        return userService.login(reqUser);
    }

    @PostMapping("/logOut")
    public Response<RespUser> logOut(@RequestBody ReqUser reqUser) {
        return userService.logOut(reqUser);
    }
}

