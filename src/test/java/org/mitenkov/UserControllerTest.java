package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserClient userClient;

    @Autowired
    DBCleaner dbCleaner;

    @BeforeEach
    public void beforeEach() throws Exception {
        dbCleaner.cleanAll();
    }

    @Test
    public void addUserTest() throws Exception {

        String username = "TestUser1";
        String password = "TestPassword1";

        UserAddRequest userAddRequest = UserAddRequest.builder()
                .username(username)
                .password(password)
                .build();


        Integer id = userClient.create(userAddRequest).id();

        UserDto user = userClient.getUser(id, username, password);

        assertEquals(userAddRequest.username(), user.username());
        assertEquals(userAddRequest.email(), user.email());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username("TestUser")
                .password("1234")
                .build());

        userClient.update(UserUpdateRequest.builder()
                .id(user.id())
                .username("Tester")
                .build());

        userClient.getUser(user.id(), "Tester", "1234");


    }

    @Test
    public void updateCurrentUserTest() throws Exception {

    }

}
