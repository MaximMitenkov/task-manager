package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mitenkov.controller.converter.UserDtoConverter;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.enums.UserRole;
import org.mitenkov.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
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

    @PostMapping
    public UserDto addUser(@RequestBody UserAddRequest request) {
        return userDtoConverter.toDto(userService.saveUser(request));
    }

    @Secured(UserRole.Values.ADMIN)
    @PutMapping
    public UserDto updateUser(@RequestBody UserAddRequest request) {
        return userDtoConverter.toDto(userService.updateUser(request));
    }

}
