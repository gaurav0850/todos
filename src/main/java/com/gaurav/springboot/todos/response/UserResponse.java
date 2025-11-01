package com.gaurav.springboot.todos.response;

import com.gaurav.springboot.todos.entity.Authority;

import java.util.List;

public class UserResponse {
    private long id;
    private String fullName;

    private String email;
    private List<Authority> authorities;

    public UserResponse(long id, String fullName, String email, List<Authority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorities = authorities;
    }

    // Getters are required so that swagger can access these fields and display in response
    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }
}