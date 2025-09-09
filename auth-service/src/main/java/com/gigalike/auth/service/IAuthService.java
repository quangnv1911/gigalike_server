package com.gigalike.auth.service;

import com.gigalike.auth.dto.request.LoginGoogleRequest;
import com.gigalike.auth.dto.response.AuthResponse;
import com.gigalike.auth.dto.request.LoginRequest;
import com.gigalike.auth.dto.request.RegisterRequest;
import com.gigalike.auth.dto.data.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(HttpServletRequest httpRequest, LoginRequest request);
    AuthResponse loginGoogle(HttpServletRequest httpRequest,LoginGoogleRequest request);
    void logout(String refreshToken);
    AuthResponse refreshToken(String refreshToken);

}
