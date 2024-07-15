package com.currencytask.currency.service;

import com.currencytask.currency.request.ReqUser;
import com.currencytask.currency.response.RespUser;
import com.currencytask.currency.response.Response;

public interface UserService {
    Response<RespUser> login(ReqUser reqUser);
}
