package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.form.user.*;
import com.example.rubiesfashionstore.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponse register(@Valid CreateUserForm form);

    UserResponse login(@Valid LoginForm form);

    UserResponse getMyProfile(Authentication authentication);

    UserResponse updateProfile(String email, UpdateUserForm form);

    void changePassword(User user, ChangePasswordForm form);

//    Quyen ADMIN:

    Page<UserResponse> getAllUsers(Pageable pageable);

    void updateUserStatus(Integer id, boolean active);

    UserResponse getUserById(Integer id);

    UserResponse updateUserbyAdmin(Integer id, UpdateUserByAdminForm form);

    void deleteUser(Integer id);

    Page<UserResponse> filterUser(FilterUserForm filterUser);
}
