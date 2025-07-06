package com.android.tabbed.service;

import com.android.tabbed.dto.LoginRequest;
import com.android.tabbed.dto.LoginResponse;
import com.android.tabbed.dto.SignupRequest;
import com.android.tabbed.entity.User;

public interface UserService {
    User signup(SignupRequest request);
    LoginResponse login(LoginRequest request);
}
