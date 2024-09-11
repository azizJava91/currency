package com.home_task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqUser {
    private String mail;
    private String password;
    private ReqToken reqToken;
    @ColumnDefault("false")
    private Boolean mailNotificationPermission;
}
