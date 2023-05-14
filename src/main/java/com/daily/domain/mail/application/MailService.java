package com.daily.domain.mail.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String[] recipients, String subject, String templateName, Map<String, Object> items) throws MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(items);

        String html = templateEngine.process(templateName, context);

        helper.setTo(recipients);
        helper.setSubject(subject);
        helper.setText(html,true);

        sender.send(message);

        log.info("Sent email to: {}", String.join(", ", recipients));
    }

}
