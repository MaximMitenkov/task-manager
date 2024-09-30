package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mitenkov.controller.converter.UserDtoConverter;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.enums.UserRole;
import org.mitenkov.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@Tag(name = "User")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Integer id) {
        return userDtoConverter.toDto(userService.getUserById(id));
    }

    @PostMapping("/reg")
    public UserDto addUser(@RequestBody UserAddRequest request) {
        return userDtoConverter.toDto(userService.saveUser(request));
    }

    @PutMapping("/current")
    public UserDto updateCurrentUser(@RequestBody UserUpdateRequest request) {
        return userDtoConverter.toDto(userService.updateCurrentUser(request));
    }

    @PutMapping("/current/password")
    public UserDto updateCurrentUserPassword(@RequestBody UserPasswordUpdateRequest request) {
        return userDtoConverter.toDto(userService.updateCurrentPassword(request));
    }

    @Secured(UserRole.Values.ADMIN)
    @PutMapping
    public UserDto updateUser(@RequestBody UserUpdateRequest request) {
        return userDtoConverter.toDto(userService.updateUser(request));
    }

    @Secured(UserRole.Values.ADMIN)
    @PutMapping("/{id}/block")
    public UserDto blockUser(@PathVariable Integer id) {
        return userDtoConverter.toDto(userService.blockUser(id));
    }

}
