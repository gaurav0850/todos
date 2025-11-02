package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.entity.Authority;
import com.gaurav.springboot.todos.entity.User;
import com.gaurav.springboot.todos.repository.UserRepository;
import com.gaurav.springboot.todos.request.PasswordUpdateRequest;
import com.gaurav.springboot.todos.response.UserResponse;
import com.gaurav.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        return new UserResponse(
                user.getId(),
                user.getFirstName().concat(" ").concat(user.getLastName()),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }

    @Override
    public void deleteUser() {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        // isLastAdmin
        if(isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself.");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        // validate old password
        String oldPassword = passwordUpdateRequest.getOldPassword();

        if (!isOldPasswordCorrect(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        // validate new and confirmed password
        String newPassword = passwordUpdateRequest.getNewPassword();
        String newPassword2 = passwordUpdateRequest.getNewPassword2();

        if (!isNewPasswordConfirmed(newPassword, newPassword2)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New Passwords do not match");
        }

        //validate if new password and old password is different
        if (!isNewAndOldPasswordDifferent(newPassword, oldPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    private boolean isOldPasswordCorrect(String oldPassword, String currentPassword) {
        return passwordEncoder.matches(oldPassword, currentPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String newPasswordConfirmation) {
        return newPassword.equals(newPasswordConfirmation);
    }

    private boolean isNewAndOldPasswordDifferent(String newPassword, String oldPassword) {
        return !newPassword.equals(oldPassword);
    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getAuthorities().stream().
                anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            return adminCount<=1;
        }

        return false;
    }
}
