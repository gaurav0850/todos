package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.entity.Authority;
import com.gaurav.springboot.todos.entity.User;
import com.gaurav.springboot.todos.repository.UserRepository;
import com.gaurav.springboot.todos.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertUserToUserResponse)
                .toList();
    }

    private UserResponse convertUserToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(" ").concat(user.getLastName()),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }
}
