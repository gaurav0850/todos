package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.request.AuthenticationRequest;
import com.gaurav.springboot.todos.request.RegisterRequest;
import com.gaurav.springboot.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest request) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
