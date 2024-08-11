package com.home_task.mailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class SenderScheduller {
    private final MailSenderService mailSenderService;


    @Scheduled(cron = "0 */2 * * * ?")
    public void sendMails() {
        System.out.println("Run at: " + LocalDateTime.now());
        mailSenderService.send();
    }
}
