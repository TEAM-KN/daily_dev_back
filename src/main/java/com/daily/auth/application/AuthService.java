package com.daily.auth.application;

import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.TokenDto;
import com.daily.auth.dto.LoginRequest;
import com.daily.auth.dto.LoginResponse;
import com.daily.auth.exception.PasswordMatchException;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.exception.NoSearchUserException;
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

        this.generateToken(user.getUsername(), response);

        return new LoginResponse(HttpStatus.OK.value(), "로그인 성공");
    }

    public CommonResponse isCheck(final String email) {
        User user = userRepository.findById(email).orElse(null);

        if (user != null) {
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");
        }

        return new CommonResponse(HttpStatus.OK, "성공" );
    }

    public CommonResponse join(final UserRequest request, final HttpServletResponse response) {
        User isUser = userRepository.findById(request.getEmail()).orElse(null);

        if (isUser != null)
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");

        userRepository.save(request.toUser(passwordEncoder));

        if (request.getSiteCodes().size() > 0) {
            List<UserSites> userSites = request.getSiteCodes().stream()
                    .map(site -> UserSites.builder()
                            .email(request.getEmail())
                            .siteCode(site)
                            .build())
                    .collect(Collectors.toList());

            userSitesRepository.saveAll(userSites);
        }
        this.generateToken(request.getEmail(), response);

        return new CommonResponse(HttpStatus.OK, "성공" );
    }

    public void generateRenewAccessToken(final AccessTokenRenewRequest request, final HttpServletResponse response) {
        String refreshToken = request.getRefreshToken();
        String payload = jwtTokenProvider.getPayload(refreshToken);
        this.generateToken(payload, response);
    }


    private void generateToken(final String payload, final HttpServletResponse response) {
        TokenDto token = new TokenDto(jwtTokenProvider.createAccessToken(payload), jwtTokenProvider.createRefreshToken(payload));
        response.addHeader(Constants.ACCESS_TOKEN.getKey(), token.getAccessToken());
        response.addHeader(Constants.REFRESH_TOKEN.getKey(), token.getRefreshToken());
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(NoSearchUserException::new);
        return user;
    }
}
