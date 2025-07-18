package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.LoginResponse;
import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.exception.NotFoundException;
import com.example.rubiesfashionstore.form.user.CreateUserForm;
import com.example.rubiesfashionstore.form.user.LoginForm;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.repository.UserRepository;
import com.example.rubiesfashionstore.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserForm form) {
        UserResponse register = userService.register(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Thành công", register));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginForm form) {
        LoginResponse login = userService.login(form);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Login thành công", login));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyProfile(Authentication authentication) {
        UserResponse response = userService.getMyProfile(authentication);
        return ResponseEntity.ok(new ApiResponse("Lấy thông tin cá nhân thành công", response));
    }
}
