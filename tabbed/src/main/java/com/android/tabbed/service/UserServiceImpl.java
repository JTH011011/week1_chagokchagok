package com.android.tabbed.service;

import com.android.tabbed.dto.LoginRequest;
import com.android.tabbed.dto.LoginResponse;
import com.android.tabbed.dto.SignupRequest;
import com.android.tabbed.entity.User;
import com.android.tabbed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.android.tabbed.exception.UserException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }

    @Override
    public User signup(SignupRequest request) {
        // UserId 중복 체크
        if (userRepository.findByUserId(request.getUsername()) != null) {
            throw new UserException("이미 등록된 사용자입니다.");
        }

        // 비밀번호 해싱
        String passwordHash = hashPassword(request.getPassword());

        // 사용자 생성
        User user = new User(request.getUsername(), passwordHash, request.getName());
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 비밀번호 검증
        if (!verifyPassword(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        // LoginResponse 생성
        return new LoginResponse(
            user.getUserId(),
            user.getUserId(),
            user.getName()
        );
    }


}
