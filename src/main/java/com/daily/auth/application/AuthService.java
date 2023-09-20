package com.daily.auth.application;

import com.daily.auth.dto.AccessTokenRenewRequest;
import com.daily.auth.dto.JwtTokenResponse;
import com.daily.auth.dto.LoginRequest;
import com.daily.auth.dto.TokenDto;
import com.daily.auth.exception.ExistUserException;
import com.daily.auth.exception.PasswordMatchException;
import com.daily.domain.site.exception.NoSearchSiteException;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.user.exception.NoSearchUserException;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.repository.UserSitesRepository;
import com.daily.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final SiteRepository siteRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenResponse login(final LoginRequest request) {
        UserDetails user = this.loadUserByUsername(request.getEmail());

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new PasswordMatchException();

        TokenDto tokenDto = this.generateToken(user.getUsername());
        return new JwtTokenResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public CommonResponse isCheck(final String email) {
        User user = userRepository.findById(email).orElse(null);

        if (user != null) {
            throw new ExistUserException();
        }

        return new CommonResponse(HttpStatus.OK, "성공");
    }

    public JwtTokenResponse join(final UserRequest request) {
        User isUser = userRepository.findById(request.getEmail()).orElse(null);

        if (isUser != null)
            throw new ExistUserException();

        userRepository.save(request.toUser(passwordEncoder));

        if (request.getSiteCodes().size() > 0) {
            if (!siteRepository.existsAllBySiteCodeIn(request.getSiteCodes()))
                throw new NoSearchSiteException();

            List<UserSites> userSites = request.getSiteCodes().stream()
                    .map(site -> UserSites.builder()
                            .email(request.getEmail())
                            .siteCode(site)
                            .build())
                    .collect(Collectors.toList());

            userSitesRepository.saveAll(userSites);
        }

        TokenDto tokenDto = this.generateToken(request.getEmail());
        return new JwtTokenResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public void generateRenewAccessToken(final AccessTokenRenewRequest request, final HttpServletResponse response) {
        String refreshToken = request.getRefreshToken();

        jwtTokenProvider.validateToken(refreshToken);
        String payload = jwtTokenProvider.getPayload(refreshToken);
    }


    private TokenDto generateToken(final String payload) {
        return new TokenDto(jwtTokenProvider.createAccessToken(payload), jwtTokenProvider.createRefreshToken(payload));
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(NoSearchUserException::new);
        return user;
    }
}
