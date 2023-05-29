package com.daily.domain.user.application;

import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.exception.NoSearchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto fetchUser(final String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null)
            throw new NoSearchUserException();

        return new UserDto(user);
    }

}
