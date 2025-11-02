package com.gaurav.springboot.todos.controller;

import com.gaurav.springboot.todos.request.PasswordUpdateRequest;
import com.gaurav.springboot.todos.response.UserResponse;
import com.gaurav.springboot.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public UserResponse getUserInfo() {
        UserResponse userInfo = userService.getUserInfo();
        return userInfo;
    }

    @DeleteMapping()
    public void deleteUser() {
        userService.deleteUser();
    }

    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) throws Exception {
        userService.updatePassword(passwordUpdateRequest);
    }
}
