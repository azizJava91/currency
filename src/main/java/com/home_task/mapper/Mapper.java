package com.home_task.mapper;

import com.home_task.dto.response.RespCurrency;
import com.home_task.entity.CurrencyEntity;

import java.util.List;


@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
RespCurrency fromEntityToResponse (CurrencyEntity currencyEntity);
List<RespCurrency> fromCurrencyEntityListToRespCurrencyList (List<CurrencyEntity> currencyEntities);

}
