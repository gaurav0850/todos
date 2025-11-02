package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.response.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();

    UserResponse promoteToAdmin(long userId);

    void deleteNonAdminUser(long userId);
}
