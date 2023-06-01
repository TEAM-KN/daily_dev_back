package com.daily.domain.user.application;

import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.exception.NoSearchUserException;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.domain.UserSitesPK;
import com.daily.domain.userSites.repository.UserSitesRepository;
import com.daily.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserSitesRepository userSitesRepository;

    @Transactional(readOnly = true)
    public UserDto fetchUser(final String email) {
        User user = userRepository.findById(email).orElseThrow(NoSearchUserException::new);
        return new UserDto(user);
    }

    public CommonResponse saveUserSites(final UserRequest.UserFromSiteRequest request) {
        userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);

        List<UserSites> userSites = request.getSiteCodes().stream()
                .map(siteCode -> UserSites.builder()
                        .email(request.getEmail())
                        .siteCode(siteCode)
                        .build())
                .collect(Collectors.toList());

        userSitesRepository.saveAll(userSites);

        return new CommonResponse(HttpStatus.OK, "성공" );
    }

    public CommonResponse deleteUserSites(final UserRequest.UserFromSiteRequest request) {
        userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);

        List<UserSitesPK> pk = request.getSiteCodes().stream()
                .map(siteCode -> UserSitesPK.builder()
                        .email(request.getEmail())
                        .siteCode(siteCode)
                        .build())
                .collect(Collectors.toList());

        userSitesRepository.deleteAllById(pk);


        return new CommonResponse(HttpStatus.OK, "성공" );
    }

}
