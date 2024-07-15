package com.currencytask.currency.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespUser {
    private String successRegistrationInfo;
    private String mailNotificationPermissionInfo;
}
