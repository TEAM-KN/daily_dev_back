package com.daily.common;

import com.daily.domain.content.repository.ContentRepository;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.userSites.repository.UserSitesRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

    @Mock
    protected ContentRepository contentRepository;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected SiteRepository siteRepository;

    @Mock
    protected UserSitesRepository userSitesRepository;

}
