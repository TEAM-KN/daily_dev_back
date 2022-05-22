package com.news.dev.api.mail.service;

import com.news.dev.api.contents.dto.ContentsResponse;
import com.news.dev.api.contents.service.ContentsService;
import com.news.dev.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailUtil mailUtil;
    private final ContentsService contentsService;


    @Override
    public void sendMail() throws Exception {

        String[] tos = new String[] {"stc9606@naver.com"};

        Map<String, Object> contentsMap = new HashMap<>();
        contentsMap.put("contents", contentsService.list());

        mailUtil.sendEmail(tos, contentsMap);

    }
}
