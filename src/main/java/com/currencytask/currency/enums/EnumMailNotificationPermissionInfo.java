package com.currencytask.currency.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumMailNotificationPermissionInfo {
    POSITIVE("The exchange rate will be sent to your email address every day"),
    SUCCESS("Registration is successful");
    public String value;

}
