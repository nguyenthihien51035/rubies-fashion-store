package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.LoginResponse;
import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.form.user.LoginForm;
import com.example.rubiesfashionstore.form.user.CreateUserForm;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponse register(@Valid CreateUserForm form);
    LoginResponse login(@Valid LoginForm form);
    UserResponse getMyProfile(Authentication authentication);
}
