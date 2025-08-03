package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.form.user.*;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.repository.UserRepository;
import com.example.rubiesfashionstore.repository.specification.UserSpecification;
import com.example.rubiesfashionstore.security.CustomUserDetails;
import com.example.rubiesfashionstore.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse register(CreateUserForm form) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new BusinessException("Email đã tồn tại", ErrorCodeConstant.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.findByPhone(form.getPhone()).isPresent()) {
            throw new BusinessException("Số điện thoại đã tồn tại", ErrorCodeConstant.PHONE_ALREADY_EXISTS);
        }

        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public UserResponse login(LoginForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new BusinessException("Thông tin đăng nhập không chính xác", ErrorCodeConstant.INVALID_LOGIN_CREDENTIALS));

        if (!user.isActive()) {
            throw new BusinessException("Tài khoản đã bị khóa", ErrorCodeConstant.USER_INACTIVE);
        }

        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new BusinessException("Thông tin đăng nhập không chính xác", ErrorCodeConstant.INVALID_LOGIN_CREDENTIALS);
        }

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getMyProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(String email, UpdateUserForm form) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));

        userRepository.findByPhone(form.getPhone())
                .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                .ifPresent(existingUser -> {
                    throw new BusinessException("Số điện thoại đã tồn tại", ErrorCodeConstant.PHONE_ALREADY_EXISTS);
                });

        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setPhone(form.getPhone());

        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserResponse.class);
    }

    @Override
    @Transactional
    public void changePassword(User user, ChangePasswordForm form) {
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Mật khẩu cũ không đúng", ErrorCodeConstant.INVALID_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    @Transactional
    public void updateUserStatus(Integer id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìn thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse updateUserbyAdmin(Integer id, UpdateUserByAdminForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));

        if (form.getPhone() != null && !form.getPhone().equals(user.getPhone())) {
            userRepository.findByPhone(form.getPhone())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new BusinessException("Số điện thoại đã tồn tại", ErrorCodeConstant.PHONE_ALREADY_EXISTS);
                    });
            user.setPhone(form.getPhone());
        }

        if (form.getFirstName() != null && !form.getFirstName().isBlank()) {
            user.setFirstName(form.getFirstName());
        }

        if (form.getLastName() != null && !form.getLastName().isBlank()) {
            user.setLastName(form.getLastName());
        }

        if (form.getRole() != null) {
            user.setRole(form.getRole());
        }

        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserResponse.class);
    }


    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));

        if (!user.isActive()) {
            throw new BusinessException("Người dùng đã bị vô hiệu hóa", ErrorCodeConstant.USER_INACTIVE);
        }

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public Page<UserResponse> filterUser(FilterUserForm filterUser) {
        Specification<User> specification = UserSpecification.firstNameContains(filterUser.getFirstName())
                .and(UserSpecification.lastNameContains(filterUser.getLastName()))
                .and(UserSpecification.emailEquals(filterUser.getEmail()))
                .and(UserSpecification.phoneEquals(filterUser.getPhone()))
                .and(UserSpecification.rolesEquals(filterUser.getRole()))
                .and(UserSpecification.isActive(filterUser.getActive()));

        Pageable pageable = PageRequest.of(filterUser.getPage(), filterUser.getSize());
        Page<User> users = userRepository.findAll(specification, pageable);

        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }
}
