package com.home_task.mailsender;

import com.home_task.cbar.CBAR;
import com.home_task.dto.response.RespCurrency;
import com.home_task.entity.CurrencyEntity;
import com.home_task.entity.UserEntity;
import com.home_task.mapper.Mapper;
import com.home_task.repository.CurrencyRepository;
import com.home_task.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final JavaMailSender javaMailSender;
    private final CBAR cbar;
    private final Mapper mapper;

    @Override
    public void send() {
        try {
            List<UserEntity> userEntities = userRepository.findAllByMailNotificationPermission(true);
            if (!userEntities.isEmpty()) {
                List<String> mails = userEntities.stream()
                        .map(UserEntity::getMail)
                        .toList();
                List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
                List<RespCurrency> respCurrencyList = mapper.fromCurrencyEntityListToRespCurrencyList(currencyEntities);

                if(currencyEntities.isEmpty()){
                   currencyEntities = cbar.updateCurrencyes();
                   respCurrencyList = mapper.fromCurrencyEntityListToRespCurrencyList(currencyEntities);
                }
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

                messageHelper.setFrom("email@example.com");
                messageHelper.setTo(mails.toArray(new String[0]));
                messageHelper.setSubject("Valyuta mezennesi");

                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append("<h2>Valyuta məzənnələri (1 AZN)</h2>");
                htmlContent.append("<table class=\"table table-striped\">");
                htmlContent.append("<thead><tr><th>Kod</th><th>Ad</th><th>Nominal</th><th>Miqdar</th></tr></thead>");
                htmlContent.append("<tbody>");
                for (RespCurrency currency : respCurrencyList) {
                    htmlContent.append("<tr>");
                    htmlContent.append("<td>").append(currency.getCode()).append("</td>");
                    htmlContent.append("<td>").append(currency.getName()).append("</td>");
                    htmlContent.append("<td>").append(currency.getNominal()).append("</td>");
                    htmlContent.append("<td>").append(currency.getValue()).append("</td>");
                    htmlContent.append("</tr>");
                }
                htmlContent.append("</tbody></table>");

                messageHelper.setText(htmlContent.toString(), true);
                javaMailSender.send(mimeMessage);
                System.out.println("email sent successfully!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
