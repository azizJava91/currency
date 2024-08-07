package com.home_task.enums;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public enum EnumMailNotificationPermissionInfo {
    POSITIVE("Registration is successfully! The exchange rate will be sent to your email address every day"),
    NEGATIVE("Registration is successfully!");

    public final String value;

}
