package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.helper.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserClient userClient;

    @Autowired
    DBCleaner dbCleaner;

    @Autowired
    EntityGenerator entityGenerator;

    @BeforeEach
    public void beforeEach() {
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

        userClient.getUser(user.id(), "TestUser", "1234");

        userClient.update(UserUpdateRequest.builder()
                .id(user.id())
                .username("Tester")
                .build());

        userClient.getUser(user.id(), "Tester", "1234");

    }

    @Test
    public void updateCurrentUserTest() throws Exception {
        String username = "TestUser";
        String password = "1234";

        UserDto user = userClient.create(UserAddRequest.builder()
                .username(username)
                .password(password)
                .build());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("Vladimir")
                .id(user.id())
                .build();

        userClient.updateCurrent(request, username, password);
    }


    @Test
    public void blockUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username("TestUser")
                .password("1234")
                .build());
        assertEquals(true, user.isActive());

        userClient.blockUser(user.id(), "admin", "admin");

        UserDto blockedUser = userClient.getUser(user.id(), "admin", "admin");
        assertEquals(false, blockedUser.isActive());

        assertEquals(user.id(), blockedUser.id());
        assertEquals(user.username(), blockedUser.username());
        assertEquals(user.email(), blockedUser.email());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.id())
                .username("BlockedUser")
                .build();

        userClient.updateCurrent(request, "TestUser", "1234");

        blockedUser = userClient.getUser(user.id(), "admin", "admin");

        assertNotEquals(request.username(), blockedUser.username());
    }

    @Test
    public void updatePasswordTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username("TestUser")
                .password("1234")
                .build());

        UserPasswordUpdateRequest request = UserPasswordUpdateRequest.builder()
                .id(user.id())
                .password("new password")
                .build();

        userClient.updatePassword(request, "admin", "admin");
        userClient.getUser(user.id(), "TestUser", "new password");
    }

}
