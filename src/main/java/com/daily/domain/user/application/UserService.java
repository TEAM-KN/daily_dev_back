package com.daily.domain.user.application;

import com.daily.auth.exception.PasswordMatchException;
import com.daily.domain.site.exception.NoSearchSiteException;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.exception.NoSearchUserException;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.repository.UserSitesRepository;
import com.daily.global.common.dto.YN;
import com.daily.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final SiteRepository siteRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto.UserWithSite fetchUser(final String email) {
        User user = userRepository.fetchUserWithSite(email).orElseThrow(NoSearchUserException::new);
        return new UserDto.UserWithSite(user);
    }

    public CommonResponse checkUser(final UserRequest.Password request) {
        User user = userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new PasswordMatchException();

        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public CommonResponse saveUser(final UserRequest.Update request) {
        userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);
        userRepository.updateUserByNickname(request.getEmail(), request.getNickname());
        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public CommonResponse savePassword(final UserRequest.Password request) {
        userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);
        userRepository.updateUserByPassword(request.getEmail(), passwordEncoder.encode(request.getPassword()));

        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public CommonResponse saveUserSites(final UserRequest.UserFromSiteRequest request) {
        userRepository.findById(request.getEmail()).orElseThrow(NoSearchUserException::new);

        if (!siteRepository.existsAllBySiteCodeIn(request.getSiteCodes()))
            throw new NoSearchSiteException();

        userSitesRepository.deleteAllByEmail(request.getEmail());

        List<UserSites> userSites = request.getSiteCodes().stream()
                .map(siteCode -> UserSites.builder()
                        .email(request.getEmail())
                        .siteCode(siteCode)
                        .build())
                .collect(Collectors.toList());

        userSitesRepository.saveAll(userSites);
        userRepository.updateUserBySubscribeYn(request.getEmail(), YN.Y.name());

        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public CommonResponse leaveUser(final String email) {
        User user = userRepository.findById(email).orElseThrow(NoSearchUserException::new);
        userRepository.delete(user);

        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public CommonResponse deleteUserSites(final String email) {
        User user = userRepository.findById(email).orElseThrow(NoSearchUserException::new);

        userSitesRepository.deleteAllByEmail(email);
        userRepository.updateUserBySubscribeYn(user.getEmail(), YN.N.name());

        return new CommonResponse(HttpStatus.OK, "성공");
    }

}
