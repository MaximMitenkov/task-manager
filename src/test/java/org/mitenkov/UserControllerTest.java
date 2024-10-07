package org.mitenkov;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.helper.AuthTestHolder;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.helper.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mitenkov.helper.AuthTestHolder.defaultPassword;
import static org.mitenkov.helper.AuthTestHolder.defaultUsername;

@RequiredArgsConstructor
public class UserControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserClient userClient;

    @Autowired
    DBCleaner dbCleaner;

    @Autowired
    EntityGenerator entityGenerator;

    @Autowired
    AuthTestHolder authHolder;

    @BeforeEach
    public void beforeEach() {
        dbCleaner.cleanAll();
    }

    @Test
    public void addUserTest() throws Exception {

        UserAddRequest userAddRequest = UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build();


        Integer id = userClient.create(userAddRequest).id();

        UserDto user = userClient.getUser(id);

        assertEquals(userAddRequest.username(), user.username());
        assertEquals(userAddRequest.email(), user.email());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        userClient.getUser(user.id());

        userClient.update(UserUpdateRequest.builder()
                .id(user.id())
                .username("Tester")
                .build());

        userClient.getUser(user.id());

    }

    @Test
    public void updateCurrentUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("Vladimir")
                .id(user.id())
                .build();

        userClient.updateCurrent(request);
        UserDto result = userClient.getUser(user.id());

        assertEquals(request.username(), result.username());
    }


    @Test
    public void blockUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());
        assertEquals(true, user.isActive());

        userClient.blockUser(user.id());
        UserDto blockedUser = userClient.getUser(user.id());

        assertEquals(false, blockedUser.isActive());
        assertEquals(user.id(), blockedUser.id());
        assertEquals(user.username(), blockedUser.username());
        assertEquals(user.email(), blockedUser.email());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.id())
                .username("BlockedUser")
                .build();

        userClient.updateCurrent(request);
        userClient.getUser(user.id());

        assertEquals(request.username(), defaultUsername);
    }

    @Test
    public void updatePasswordTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        UserPasswordUpdateRequest request = UserPasswordUpdateRequest.builder()
                .id(user.id())
                .password("new password")
                .build();

        userClient.updatePassword(request);
        userClient.getUser(user.id());
    }

}
