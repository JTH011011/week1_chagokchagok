package com.android.tabbed.controller;

import com.android.tabbed.dto.LoginRequest;
import com.android.tabbed.dto.LoginResponse;
import com.android.tabbed.dto.SignupRequest;
import com.android.tabbed.dto.SignupResponse;
import com.android.tabbed.entity.User;
import com.android.tabbed.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.android.tabbed.exception.UserException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.signup(request);
            SignupResponse response = new SignupResponse();
            response.setUserId(user.getUserId());
            response.setUsername(user.getUserId());
            response.setName(user.getName());
            response.setMessage("User created successfully");
            return ResponseEntity.ok(response);
        } catch (UserException e) {
            SignupResponse errorResponse = new SignupResponse();
            errorResponse.setMessage("이미 등록된 사용자입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
