package com.home_task.util;

import com.home_task.dto.request.ReqToken;
import com.home_task.entity.Token;
import com.home_task.entity.UserEntity;
import com.home_task.enums.EnumAvailableStatus;
import com.home_task.exception.CurrencyException;
import com.home_task.exception.ExceptionConstants;
import com.home_task.repository.TokenRepository;
import com.home_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Util {

private final UserRepository userRepository;
private final TokenRepository tokenRepository;
    public Token checkToken(ReqToken reqToken, String mail) {



        String tokenString = reqToken.getToken();

        if (mail == null || tokenString == null) {
            throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }

        UserEntity user = userRepository.findByMail(mail);
        if (user == null) {
            throw new CurrencyException(ExceptionConstants.USER_NOT_FOUND, "User not found");
        }
       Token token = tokenRepository.findByUserEntityAndTokenAndActive(user, tokenString, EnumAvailableStatus.ACTIVE.value);
        if (token == null) {
            throw new CurrencyException(ExceptionConstants.USER_TOKEN_NOT_FOUND, "token expired or invalid");
        }
        return token;
    }
}
