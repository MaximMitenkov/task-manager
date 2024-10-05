package org.mitenkov;

import lombok.RequiredArgsConstructor;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    final String defaultUsername = "TestUser";

    final String defaultPassword = "1234";

    @Value("${app.admin.username}")
    final String adminUsername;

    @Value("${app.admin.password}")
    final String adminPassword;

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

        UserDto user = userClient.getUser(id, defaultUsername, defaultPassword);

        assertEquals(userAddRequest.username(), user.username());
        assertEquals(userAddRequest.email(), user.email());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());

        userClient.getUser(user.id(), defaultUsername, defaultPassword);

        userClient.update(UserUpdateRequest.builder()
                .id(user.id())
                .username("Tester")
                .build());

        userClient.getUser(user.id(), "Tester", defaultPassword);

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

        userClient.updateCurrent(request, defaultUsername, defaultPassword);
        UserDto result = userClient.getUser(user.id(), adminUsername, adminPassword);

        assertEquals(request.username(), result.username());
    }


    @Test
    public void blockUserTest() throws Exception {
        UserDto user = userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());
        assertEquals(true, user.isActive());

        userClient.blockUser(user.id(), adminUsername, adminPassword);
        UserDto blockedUser = userClient.getUser(user.id(), adminUsername, adminPassword);

        assertEquals(false, blockedUser.isActive());
        assertEquals(user.id(), blockedUser.id());
        assertEquals(user.username(), blockedUser.username());
        assertEquals(user.email(), blockedUser.email());

        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.id())
                .username("BlockedUser")
                .build();

        userClient.updateCurrent(request, defaultUsername, adminPassword);
        userClient.getUser(user.id(), adminUsername, adminPassword);

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
        userClient.getUser(user.id(), defaultUsername, "new password");
    }

}
