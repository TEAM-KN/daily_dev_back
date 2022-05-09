package com.news.dev.adaptor;

import com.news.dev.auth.user.dto.UserDto;
import com.news.dev.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailServiceAdaptor {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    /*public List<UserDto> sendToSubscriber() {

        try {

        } catch (NullPointerException e) {
            log.error("Subscriber is Null");
        }

    }*/

}
