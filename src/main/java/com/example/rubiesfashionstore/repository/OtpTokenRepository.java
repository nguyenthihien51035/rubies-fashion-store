package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Integer> {
    Optional<OtpToken> findByEmail(String email);

    Optional<OtpToken> findTopByEmailOrderByIdDesc(String email);
}
