package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.form.user.*;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.security.CustomUserDetails;
import com.example.rubiesfashionstore.service.EmailService;
import com.example.rubiesfashionstore.service.ForgotPasswordService;
import com.example.rubiesfashionstore.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    public final UserService userService;
    public final EmailService emailService;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserForm form) {
        UserResponse register = userService.register(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Thành công", register));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginForm form) {
        UserResponse login = userService.login(form);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Login thành công", login));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyProfile(Authentication authentication) {
        UserResponse response = userService.getMyProfile(authentication);
        return ResponseEntity.ok(new ApiResponse("Lấy thông tin cá nhân thành công", response));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@Valid @RequestBody UpdateUserForm form, Authentication authentication) {
        String email = authentication.getName();
        UserResponse response = userService.updateProfile(email, form);
        return ResponseEntity.ok(new ApiResponse("Cập nhật thông tin thành công", response));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordForm form, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        userService.changePassword(user, form);
        return ResponseEntity.ok(new ApiResponse("Đổi mật khẩu thành công", null));
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordForm form) {
        forgotPasswordService.sendOtp(form);
        return ResponseEntity.ok(new ApiResponse("Đã gửi mã OTP qua email", null));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordForm form) {
        forgotPasswordService.resetPassword(form);
        return ResponseEntity.ok(new ApiResponse("Đặt lại mật khẩu thành công", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers(Pageable pageable) {
        Page<UserResponse> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(new ApiResponse("Lấy danh sách người dùng thành công", page));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/active/{id}")
    public ResponseEntity<ApiResponse> updateUserStatus(@PathVariable Integer id, @Valid @RequestBody UpdateUserStatusForm form) {
        userService.updateUserStatus(id, form.getActive());
        return ResponseEntity.ok(new ApiResponse("Cập nhật trạng thái người dùng thành công", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Integer id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse("Lấy thông tin người dùng thành công", response));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUserByAdmin(@PathVariable Integer id, @Valid @RequestBody UpdateUserByAdminForm form) {
        UserResponse response = userService.updateUserbyAdmin(id, form);
        return ResponseEntity.ok(new ApiResponse("Cập nhật người dùng thành công", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("Xóa người dùng thành công", null));
    }

    @GetMapping("filter")
    public ResponseEntity<ApiResponse> filterUser(FilterUserForm filterUserForm) {
        Page<UserResponse> page = userService.filterUser(filterUserForm);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Thành công", page));
    }
}
