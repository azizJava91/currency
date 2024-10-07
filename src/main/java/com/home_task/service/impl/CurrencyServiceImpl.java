package com.home_task.service.impl;

import com.home_task.cbar.CBAR;
import com.home_task.dto.request.ReqCurrency;
import com.home_task.dto.response.RespCurrency;
import com.home_task.dto.response.RespStatus;
import com.home_task.dto.response.Response;
import com.home_task.entity.CurrencyEntity;
import com.home_task.entity.Token;
import com.home_task.entity.UserEntity;
import com.home_task.enums.EnumAvailableStatus;
import com.home_task.exception.CurrencyException;
import com.home_task.exception.ExceptionConstants;
import com.home_task.mapper.Mapper;
import com.home_task.repository.CurrencyRepository;
import com.home_task.repository.TokenRepository;
import com.home_task.repository.UserRepository;
import com.home_task.service.interfaces.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository currencyRepository;
    private final Mapper mapper;
    private final CBAR cbar;
    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    public Response<RespCurrency> compare(ReqCurrency reqCurrency) {
        Response response = new Response<>();

        try {
            checkRequest(reqCurrency);

            if (reqCurrency.getFromCode() == null || reqCurrency.getQuantity() == null) {
                throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data, fields required ");
            }

            List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
            if (currencyEntities.isEmpty()) {
                cbar.updateCurrencyes();
            }
            CurrencyEntity currencyEntity = currencyRepository.findByCode(reqCurrency.getFromCode().toUpperCase());
            if (currencyEntity == null) {
                throw new CurrencyException(ExceptionConstants.CURRENCY_NOT_FOUND, "Currency not found");
            }
            RespCurrency respCurrency = mapper.fromEntityToResponse(currencyEntity);
            respCurrency.setValue(currencyEntity.getValue().multiply(reqCurrency.getQuantity()));
            respCurrency.setNominal(reqCurrency.getQuantity());
            respCurrency.setCode(currencyEntity.getCode());
            respCurrency.setName(currencyEntity.getName());

            response.setT(respCurrency);
            response.setRespons_Status(RespStatus.getSuccessMessage());

        } catch (CurrencyException ce) {
            ce.printStackTrace();
            response.setRespons_Status(new RespStatus(ce.getCode(), ce.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }


    @Override
    public Response<List<RespCurrency>> getCurrencyList(ReqCurrency reqCurrency) {
        Response response = new Response<>();
        try {
            checkRequest(reqCurrency);

            List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
            if (currencyEntities.isEmpty()) {
                cbar.updateCurrencyes();
                currencyEntities = currencyRepository.findAll();
            }
            List<RespCurrency> respCurrencies = mapper.fromCurrencyEntityListToRespCurrencyList(currencyEntities);
            response.setT(respCurrencies);
            response.setRespons_Status(RespStatus.getSuccessMessage());

        } catch (CurrencyException ce) {
            ce.printStackTrace();
            response.setRespons_Status(new RespStatus(ce.getCode(), ce.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespons_Status(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "internal exception"));
        }
        return response;
    }

    private void checkRequest(ReqCurrency reqCurrency) {
        if (reqCurrency.getMail() == null || reqCurrency.getPassword() == null || reqCurrency.getReqToken() == null) {
            throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        UserEntity userentity = userRepository.findByMailAndPassword(reqCurrency.getMail(), reqCurrency.getPassword());
        if (userentity == null) {
            throw new CurrencyException(ExceptionConstants.USER_NOT_FOUND, "User not found ");
        }
        Token token = tokenRepository.findByUserEntityAndTokenAndActive(userentity, reqCurrency.getReqToken().getToken(), EnumAvailableStatus.ACTIVE.value);
        if (token == null) {
            throw new CurrencyException(ExceptionConstants.USER_TOKEN_NOT_FOUND, "token expired or invalid");
        }
    }
}
