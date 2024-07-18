package com.home_task.dto.request;

import lombok.Data;

@Data
public class ReqUser {
    private String mail;
    private String password;
    private String mailNotificationPermission;
}
