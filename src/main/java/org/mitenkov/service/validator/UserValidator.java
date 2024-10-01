package org.mitenkov.service.validator;

import org.mitenkov.entity.User;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.exception.ErrorCodeException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validate(User user) {
        if (!user.isActive()) {
            throw new ErrorCodeException(ErrorCode.ACCESS_DENIED);
        }
    }

}
