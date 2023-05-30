package com.daily.auth.application;

import com.daily.common.exception.dto.ErrorCode;
import com.daily.common.file.FileUtils;
import com.daily.common.response.CommonResponse;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.repository.UserSitesRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserSitesRepository userSitesRepository;
    private final FileUtils fileUtils;

    public CommonResponse isCheck(final String email) {
        User user = userRepository.findById(email).orElse(null);

        if (user != null) {
            return new CommonResponse(ErrorCode.DUPLICATE_USER);
        }

        return new CommonResponse(ErrorCode.SUCCESS);
    }

    public CommonResponse join(final UserRequest joinRequest) throws IOException {
        User isUser = userRepository.findById(joinRequest.getEmail()).orElse(null);

        if (isUser != null)
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");

        String filePath = fileUtils.storeFile(joinRequest.getImageFile());
        userRepository.save(joinRequest.toUser(filePath));

        if (joinRequest.getSiteCodes().size() > 0) {
            List<UserSites> userSites = joinRequest.getSiteCodes().stream()
                    .map(site -> UserSites.builder()
                            .email(joinRequest.getEmail())
                            .siteCode(site)
                            .build())
                    .collect(Collectors.toList());

            userSitesRepository.saveAll(userSites);
        }

        return new CommonResponse(ErrorCode.SUCCESS);
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return user;
    }
}
