package com.home_task.cbar;
import com.home_task.entity.CurrencyEntity;

import java.io.IOException;
import java.util.List;

public interface CBAR {
    List<CurrencyEntity> updateCurrencyes() throws IOException;
}
