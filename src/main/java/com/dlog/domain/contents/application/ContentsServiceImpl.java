package com.dlog.domain.contents.application;

import com.dlog.adaptor.KakaoAdaptor;
import com.dlog.adaptor.WoowahanAdaptor;
import com.dlog.domain.contents.domain.ContentsRepository;
import com.dlog.domain.contents.dto.ContentsResponse;
import com.dlog.domain.contents.domain.Contents;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final WoowahanAdaptor woowahanAdaptor;
    private final KakaoAdaptor kakaoAdaptor;
    private final ContentsRepository contentsRepository;

    @Override
    @Cacheable("contents")
    @Transactional(readOnly = true)
    public List<ContentsResponse> list() {

        // 1. Get Contents
        List<Contents> contentsList = contentsRepository.findAll();

        // 2. Convert Response
        List<ContentsResponse> contentsResponse = contentsList.stream().map(
                content -> new ModelMapper().map(content, ContentsResponse.class)
            ).collect(Collectors.toList());

        return contentsResponse;
    }

    @Override
    public void update() {
        // 1. Contents Update
//        List<ContentsDto> contents = woowahanAdaptor.initContents();
//
//
//        List<ContentsEntity> contentsEntityList = contents.stream().map(
//                content -> new ModelMapper().map(content, ContentsEntity.class)
//            ).collect(Collectors.toList());
//
//        contentsRepository.saveAll(contentsEntityList);
    }
}
