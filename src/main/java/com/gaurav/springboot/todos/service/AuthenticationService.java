package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.request.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest request) throws Exception;
}
