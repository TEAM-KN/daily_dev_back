package com.daily.user.application;

import com.daily.user.domain.User;
import com.daily.user.exception.NoSearchUserException;
import com.daily.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User fetchUser(final String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null)
            throw new NoSearchUserException();

        return user;
    }

}
