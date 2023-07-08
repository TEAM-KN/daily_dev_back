package com.daily.auth.application;

import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.AccessTokenRenewResponse;
import com.daily.auth.dto.LoginRequest;
import com.daily.auth.dto.LoginResponse;
import com.daily.auth.exception.PasswordMatchException;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.repository.UserSitesRepository;
import com.daily.global.common.dto.Constants;
import com.daily.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserSitesRepository userSitesRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(final LoginRequest request, final HttpServletResponse response) {
        UserDetails user = this.loadUserByUsername(request.getEmail());

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new PasswordMatchException();

        response.addHeader(Constants.ACCESS_TOKEN.getKey(), Constants.BEARER + jwtTokenProvider.createAccessToken(user.getUsername()));
        response.addHeader(Constants.REFRESH_TOKEN.getKey(), Constants.BEARER + jwtTokenProvider.createRefreshToken(user.getUsername()));

        return new LoginResponse(HttpStatus.OK.value(), "로그인 성공");
    }

    public CommonResponse isCheck(final String email) {
        User user = userRepository.findById(email).orElse(null);

        if (user != null) {
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");
        }

        return new CommonResponse(HttpStatus.OK, "성공" );
    }

    public CommonResponse join(final UserRequest joinRequest) {
        User isUser = userRepository.findById(joinRequest.getEmail()).orElse(null);

        if (isUser != null)
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");

        userRepository.save(joinRequest.toUser());

        if (joinRequest.getSiteCodes().size() > 0) {
            List<UserSites> userSites = joinRequest.getSiteCodes().stream()
                    .map(site -> UserSites.builder()
                            .email(joinRequest.getEmail())
                            .siteCode(site)
                            .build())
                    .collect(Collectors.toList());

            userSitesRepository.saveAll(userSites);
        }

        return new CommonResponse(HttpStatus.OK, "성공" );
    }

    public AccessTokenRenewResponse generateAccessToken(final AccessTokenRenewRequest request) {
        String refreshToken = request.getRefreshToken();
        String payload = jwtTokenProvider.getPayload(refreshToken);

        return new AccessTokenRenewResponse(jwtTokenProvider.createAccessToken(payload), jwtTokenProvider.createRefreshToken(payload));
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return user;
    }
}
