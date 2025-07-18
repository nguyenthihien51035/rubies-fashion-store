package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.LoginResponse;
import com.example.rubiesfashionstore.dto.response.UserResponse;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.form.user.CreateUserForm;
import com.example.rubiesfashionstore.form.user.LoginForm;
import com.example.rubiesfashionstore.jwt.JwtUtil;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.repository.UserRepository;
import com.example.rubiesfashionstore.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public UserResponse register(CreateUserForm form) {
        if(userRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new BusinessException("Email đã tồn tại", ErrorCodeConstant.EMAIL_ALREADY_EXISTS);
        }

        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public LoginResponse login(LoginForm form) {
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new BusinessException("Thông tin đăng nhập không chính xác", ErrorCodeConstant.INVALID_LOGIN_CREDENTIALS));

        if(!user.isActive()) {
            throw new BusinessException("Tài khoản đã bị khóa", ErrorCodeConstant.USER_INACTIVE);
        }

        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new BusinessException("Thông tin đăng nhập không chính xác", ErrorCodeConstant.INVALID_LOGIN_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new LoginResponse(token, userResponse);
    }

    @Override
    public UserResponse getMyProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return modelMapper.map(user, UserResponse.class);
    }
}
