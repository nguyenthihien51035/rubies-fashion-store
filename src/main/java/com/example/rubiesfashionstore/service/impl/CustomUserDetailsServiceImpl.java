package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.repository.UserRepository;
import com.example.rubiesfashionstore.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .filter(User::isActive)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));
    }
}
