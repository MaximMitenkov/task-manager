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
import static org.mitenkov.helper.AuthTestHolder.*;

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
        dbCleaner.reset();
        authHolder.setCurrentUser();
    }

    @Test
    public void addUserTest() throws Exception {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build();

        int id = userClient.create(userAddRequest).id();

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

        int id = user.id();
        int status = userClient.getUserRequestStatus(id);
        assertEquals(200, status);

        String newUsername = "Tester";

        userClient.update(UserUpdateRequest.builder()
                .id(id)
                .username(newUsername)
                .build());
        UserDto updatedUser = userClient.getUser(id);

        authHolder.setCurrentUser(newUsername, defaultPassword);
        status = userClient.getUserRequestStatus(id);
        assertEquals(200, status);
        assertEquals(newUsername, updatedUser.username());
    }

    @Test
    public void updateCurrentUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        String newUsername = "Vladimir";
        int id = user.id();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .username(newUsername)
                .id(id)
                .build();

        authHolder.setCurrentUser(defaultUsername, defaultPassword);
        int status = userClient.updateCurrent(request);
        assertEquals(200, status);

        status = userClient.getUserRequestStatus(id);
        assertEquals(401, status);

        authHolder.setCurrentUser(newUsername, defaultPassword);
        status = userClient.getUserRequestStatus(id);
        assertEquals(200, status);
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

        userClient.blockUser(user.id());
        blockedUser = userClient.getUser(user.id());
        assertEquals(false, blockedUser.isActive());

        authHolder.setCurrentUser(defaultUsername, defaultPassword);
        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.id())
                .username("BlockedUser")
                .build();
        int status = userClient.updateCurrent(request);
        assertEquals(401, status);

        authHolder.setCurrentUser();
        UserDto resultUser = userClient.getUser(user.id());
        assertEquals(defaultUsername, resultUser.username());
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
        int status = userClient.updatePassword(request);
        assertEquals(200, status);

        status = userClient.getUserRequestStatus(user.id());
        assertEquals(401, status);

        authHolder.setCurrentUser(defaultUsername, "new password");
        status = userClient.getUserRequestStatus(user.id());
        assertEquals(200, status);
    }

    @Test
    public void unblockUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        int id = user.id();
        userClient.blockUser(id);

        authHolder.setCurrentUser(defaultUsername, defaultPassword);
        int status = userClient.getUserRequestStatus(id);
        assertEquals(401, status);

        authHolder.setCurrentUser();
        userClient.unblockUser(id);

        authHolder.setCurrentUser(defaultUsername, defaultPassword);
        status = userClient.getUserRequestStatus(id);
        assertEquals(200, status);
    }
}
