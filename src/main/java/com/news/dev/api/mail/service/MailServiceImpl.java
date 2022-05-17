package com.news.dev.api.mail.service;

import com.news.dev.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailUtil mailUtil;


    @Override
    public void sendMail() throws Exception {

        String[] tos = new String[] {"stc9606@naver.com"};

        mailUtil.sendEmail(tos);

    }
}
