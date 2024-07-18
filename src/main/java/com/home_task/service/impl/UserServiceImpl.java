package com.home_task.service.impl;


import com.home_task.dto.request.ReqUser;
import com.home_task.dto.response.RespStatus;
import com.home_task.dto.response.RespUser;
import com.home_task.dto.response.Response;
import com.home_task.entity.UserEntity;
import com.home_task.enums.EnumMailNotificationPermissionInfo;
import com.home_task.exception.CurrencyException;
import com.home_task.exception.ExceptionConstants;
import com.home_task.repository.UserRepository;
import com.home_task.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Response<RespUser> register(ReqUser reqUser) {

        Response response = new Response<>();


        try {
            UserEntity available = userRepository.findByMail(reqUser.getMail());
            if (available != null) {
                throw new CurrencyException(ExceptionConstants.MAIL_IS_IN_USE, "Mail is in use");
            }
            UserEntity userEntity = UserEntity.builder()
                    .mail(reqUser.getMail())
                    .password(reqUser.getPassword())
                    .mailNotificationPermission(reqUser.getMailNotificationPermission())
                    .build();
            if (reqUser.getMail() == null || reqUser.getPassword() == null || reqUser.getMailNotificationPermission() == null) {
                throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data, fields required");
            }
            userRepository.save(userEntity);

            RespUser respUser = RespUser.builder()
                    .loginInfo(EnumMailNotificationPermissionInfo.NEGATIVE.value)
                    .build();
            if (reqUser.getMailNotificationPermission().equals("yes")) {
                respUser.setLoginInfo(EnumMailNotificationPermissionInfo.POSITIVE.value);
            }
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
}
