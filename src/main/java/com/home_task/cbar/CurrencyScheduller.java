package com.home_task.cbar;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CurrencyScheduller {

    private final CBAR cbar;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCurrencyRates() throws IOException {
        cbar.updateCurrencyes();

    }
}
