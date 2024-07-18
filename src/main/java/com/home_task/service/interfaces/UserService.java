package com.home_task.service.interfaces;


import com.home_task.dto.request.ReqUser;
import com.home_task.dto.response.RespUser;
import com.home_task.dto.response.Response;

public interface UserService {
    Response<RespUser> register(ReqUser reqUser);
}
