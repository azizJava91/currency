package com.currencytask.currency.service.impl;

import com.currencytask.currency.enums.EnumMailNotificationPermissionInfo;
import com.currencytask.currency.entity.UserEntity;
import com.currencytask.currency.repository.UserRepository;
import com.currencytask.currency.request.ReqUser;
import com.currencytask.currency.response.RespStatus;
import com.currencytask.currency.response.RespUser;
import com.currencytask.currency.response.Response;
import com.currencytask.currency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Response<RespUser> login(ReqUser reqUser) {

        Response response = new Response();
        try {
//            UserEntity userEntity = UserEntity.builder()
//                    .mail(reqUser.getMail())
//                    .password(reqUser.getPassword())
//                    .mailNotificationPermission(reqUser.getMailNotificationPermission())
//                    .build();
//            userRepository.save(userEntity);

            RespUser respUser = RespUser.builder()
                    .successRegistrationInfo(EnumMailNotificationPermissionInfo.SUCCESS.value)
                    .mailNotificationPermissionInfo(EnumMailNotificationPermissionInfo.POSITIVE.value)
                    .build();

            response.setT(EnumMailNotificationPermissionInfo.SUCCESS);
            response.setRespons_Status(RespStatus.getSuccessMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
