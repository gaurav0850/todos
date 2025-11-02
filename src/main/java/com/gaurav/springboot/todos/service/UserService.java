package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.request.PasswordUpdateRequest;
import com.gaurav.springboot.todos.response.UserResponse;

public interface UserService {

    UserResponse getUserInfo();
    void deleteUser();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
