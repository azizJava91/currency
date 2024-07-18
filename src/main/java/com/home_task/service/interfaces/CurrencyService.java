package com.home_task.service.interfaces;
import com.home_task.dto.request.ReqCurrency;
import com.home_task.dto.response.RespCurrency;
import com.home_task.dto.response.Response;
import java.util.List;

public interface CurrencyService {


    Response<RespCurrency> compare(ReqCurrency reqCurrency);

    Response<List<RespCurrency>> getCurrencyList(ReqCurrency reqCurrency);

}
