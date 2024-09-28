package org.mitenkov;

import org.junit.jupiter.api.Test;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends BaseTest{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userController;

    @Test
    public void addUserTest() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .username("TestUser1")
                .password("1234")
                .build();

        User savedUser = userController.saveUser(userAddRequest);

        assertEquals(userAddRequest.username(), savedUser.getUsername());
        assertEquals(passwordEncoder.encode(userAddRequest.password()), savedUser.getPassword());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserUpdateRequest request = UserUpdateRequest.builder()

                .build();
    }

}
