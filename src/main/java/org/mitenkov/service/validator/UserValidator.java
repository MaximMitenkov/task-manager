package org.mitenkov.service.validator;

import lombok.RequiredArgsConstructor;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.exception.ErrorCodeException;
import org.mitenkov.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUnique(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ErrorCodeException(ErrorCode.VALIDATION_ERROR);
        }
    }

}
