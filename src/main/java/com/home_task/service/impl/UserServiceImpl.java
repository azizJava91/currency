package com.home_task.service.impl;


import com.home_task.dto.request.ReqUser;
import com.home_task.dto.response.RespStatus;
import com.home_task.dto.response.RespUser;
import com.home_task.dto.response.Response;
import com.home_task.entity.Token;
import com.home_task.entity.UserEntity;
import com.home_task.enums.EnumAvailableStatus;
import com.home_task.enums.EnumMailNotificationPermissionInfo;
import com.home_task.exception.CurrencyException;
import com.home_task.exception.ExceptionConstants;
import com.home_task.repository.TokenRepository;
import com.home_task.repository.UserRepository;
import com.home_task.service.interfaces.UserService;
import com.home_task.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final Util util;

    public Response<RespUser> register(ReqUser reqUser) {

        Response response = new Response<>();


        try {
            if (reqUser.getMail() == null || reqUser.getPassword() == null) {
                throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data, fields required");
            }

            UserEntity available = userRepository.findByMail(reqUser.getMail());
            if (available != null) {
                throw new CurrencyException(ExceptionConstants.MAIL_IS_IN_USE, "Mail is in use");
            }

            UserEntity userEntity = UserEntity.builder()
                    .mail(reqUser.getMail())
                    .password(reqUser.getPassword())
                    .mailNotificationPermission(reqUser.getMailNotificationPermission())
                    .build();
            userRepository.save(userEntity);

            String tokenUUID = UUID.randomUUID().toString();

            Token token = Token.builder()
                    .token(tokenUUID)
                    .userEntity(userEntity)
                    .build();
            tokenRepository.save(token);

            RespUser respUser = RespUser.builder()
                    .loginInfo(reqUser.getMailNotificationPermission().equals(true)
                            ? EnumMailNotificationPermissionInfo.POSITIVE.value
                            : EnumMailNotificationPermissionInfo.NEGATIVE.value)
                    .token(tokenUUID)
                    .build();

            response.setT(respUser);
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

    public Response<RespUser> login(ReqUser reqUser) {
        Response response = new Response<>();
        try {
            UserEntity available = userRepository.findByMailAndPassword(reqUser.getMail(), reqUser.getPassword());
            if (available == null) {
                throw new CurrencyException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            Token token = util.checkToken(reqUser.getReqToken(), reqUser.getMail());
            RespUser respUser = RespUser.builder()
                    .token(token.getToken())
                    .build();
            response.setT(respUser);
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
    public Response<RespUser> logOut(ReqUser reqUser) {
        Response response = new Response<>();
        try {
            UserEntity available = userRepository.findByMailAndPassword(reqUser.getMail(), reqUser.getPassword());
            System.out.println(reqUser.getMail());
            System.out.println(reqUser.getPassword());
            if (available == null) {
                throw new CurrencyException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            Token token = util.checkToken(reqUser.getReqToken(), reqUser.getMail());

            token.setActive(EnumAvailableStatus.DEACTIVE.value);
            tokenRepository.save(token);
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


}
