package com.home_task.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1), DEACTIVE(0);

    public Integer value;
}
