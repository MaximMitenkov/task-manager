package org.mitenkov.service;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.entity.User;
import org.mitenkov.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(UserAddRequest request) {
        return userRepository.save(User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .build());
    }

    public User updateUser(UserAddRequest request) {
        return userRepository.save(User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .build());
    }

}
