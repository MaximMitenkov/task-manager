package org.mitenkov.helper;

import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.User;
import org.mitenkov.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTestHolder {
    public static User currentUser;

    public static String currentUserPassword;

    public static String adminUsername = "admin";

    public static String adminPassword = "12345";

    public static String defaultUsername = "TestUsername";

    public static String defaultPassword = "1234";

    private final UserRepository userRepository;

    public void setCurrentUser() {
        currentUser = userRepository.findByUsername(adminUsername);
        currentUserPassword = adminPassword;
    }

    public void setCurrentUser(String username, String password) {
        currentUser = userRepository.findByUsername(username);
        currentUserPassword = password;
    }

}
