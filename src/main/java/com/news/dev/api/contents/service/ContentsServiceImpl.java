package com.news.dev.api.contents.service;

import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.api.contents.dto.ContentsRequest;
import com.news.dev.api.contents.dto.ContentsResponse;
import com.news.dev.auth.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final WoowahanAdaptor woowahanAdaptor;

    @Override
    public List<ContentsResponse> list(ContentsRequest contentsRequest) {

        // 1. Get Contents
        List<ContentsDto> contents = woowahanAdaptor.getContents();

        // 2. Convert Response
        List<ContentsResponse> contentsResponse = contents.stream().map(
                content -> new ModelMapper().map(content, ContentsResponse.class)
            ).collect(Collectors.toList());

        return contentsResponse;
    }
}
