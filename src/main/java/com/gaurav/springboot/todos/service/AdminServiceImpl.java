package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.entity.Authority;
import com.gaurav.springboot.todos.entity.User;
import com.gaurav.springboot.todos.repository.UserRepository;
import com.gaurav.springboot.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()
                || userOptional.get().getAuthorities().stream().
                anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an admin");
        }
        User user = userOptional.get();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        authorities.add(new Authority("ROLE_ADMIN"));

        user.setAuthorities(authorities);

        return convertUserToUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteNonAdminUser(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()
                || userOptional.get().getAuthorities().stream().
                anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist or already an admin");
        }
        User user = userOptional.get();
        userRepository.delete(user);
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
