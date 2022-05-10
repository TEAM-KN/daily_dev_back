package com.news.dev.util;

import com.news.dev.api.subscriber.dto.SubscriberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailUtil {

    private final JavaMailSender sender;

    public void sendEmail(String[] address) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(address);
            helper.setSubject("메일 테스트");
            helper.setText("확인");
        } catch (MessagingException e) {
            log.error("Mail Send error...");
        }

        sender.send(message);
    }

}
