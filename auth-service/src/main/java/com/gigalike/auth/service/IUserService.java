package com.gigalike.auth.service;

import com.gigalike.auth.dto.data.UserDto;
import com.gigalike.auth.dto.request.UpdateUserRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface IUserService {
    UserDto getCurrentUser();
    UserDto getUserById(UUID userId);
    void updateUserInfo(UUID userId, UpdateUserRequest updateUserRequest);

    void updateUserIpValid(UUID userId, String ip);
}
