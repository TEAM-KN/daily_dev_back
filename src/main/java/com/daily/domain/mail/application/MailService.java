package com.daily.domain.mail.application;

import lombok.RequiredArgsConstructor;
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
public class MailService {

    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String recipient, String subject, String templateName, Map<String, Object> content) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(content);

        String html = templateEngine.process(templateName, context);

        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(html,true);

        sender.send(message);
    }

}
