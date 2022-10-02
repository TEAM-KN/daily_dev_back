package com.dlog.util;

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
public class MailUtil {

    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String[] address, Map<String, Object> items) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(address);
            helper.setSubject("새로운 기술 블로그 도착했습니다~ :)");

            // 템플릿에 전달할 데이터
            Context context = new Context();
            context.setVariables(items);

            // 메일 내용 설정 (Template)
            String html = templateEngine.process("mail", context);
            helper.setText(html,true);

        } catch (MessagingException e) {
            log.error("Mail Send error...");
        }

        sender.send(message);
    }

}
