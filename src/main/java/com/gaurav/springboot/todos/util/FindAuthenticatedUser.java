package com.gaurav.springboot.todos.util;

import com.gaurav.springboot.todos.entity.User;

public interface FindAuthenticatedUser {

    User getAuthenticatedUser();
}
