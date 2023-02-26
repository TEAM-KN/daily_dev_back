package com.dlog.domain.contents.application;

import com.dlog.domain.contents.repository.ContentsRepository;
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

    private final ContentsRepository contentsRepository;

    @Override
    @Cacheable("contents")
    @Transactional(readOnly = true)
    public List<ContentsResponse> list() {

        // 1. Get Contents
        List<Contents> contentsList = contentsRepository.findAll();

        // 2. Convert Response
        List<ContentsResponse> contentsResponse = contentsList.stream()
                .map(content -> new ModelMapper().map(content, ContentsResponse.class))
                .collect(Collectors.toList());

        return contentsResponse;
    }

    @Override
    public void update() {}
}
