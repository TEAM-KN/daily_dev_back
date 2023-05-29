package com.daily.auth.application;

import com.daily.comn.exception.dto.ErrorCode;
import com.daily.comn.file.FileUtils;
import com.daily.comn.response.CommonResponse;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserJoinRequest;
import com.daily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final FileUtils fileUtils;

    public CommonResponse isCheck(final String email) {
        User user = userRepository.findById(email).orElse(null);

        if (user != null) {
            return new CommonResponse(ErrorCode.DUPLICATE_USER);
        }

        return new CommonResponse(ErrorCode.SUCCESS);
    }

    public CommonResponse join(final UserJoinRequest joinRequest) throws IOException {
        User isUser = userRepository.findById(joinRequest.getEmail()).orElse(null);

        if (isUser != null)
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");

        String filePath = fileUtils.storeFile(joinRequest.getImageFile());
        userRepository.save(joinRequest.toUser(filePath));

        return new CommonResponse(ErrorCode.SUCCESS);
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return user;
    }
}
