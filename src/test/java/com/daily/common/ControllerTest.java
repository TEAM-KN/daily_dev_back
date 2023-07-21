package com.daily.common;

import com.daily.auth.application.AuthService;
import com.daily.auth.application.JwtTokenProvider;
import com.daily.auth.controller.AuthController;
import com.daily.domain.content.application.ContentService;
import com.daily.domain.content.controller.ContentController;
import com.daily.domain.site.application.SiteService;
import com.daily.domain.site.controller.SiteController;
import com.daily.domain.user.application.UserService;
import com.daily.domain.user.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest({
        AuthController.class,
        UserController.class,
        ContentController.class,
        SiteController.class
})
@ActiveProfiles("local")
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected ContentService contentService;

    @MockBean
    protected SiteService siteService;

}
