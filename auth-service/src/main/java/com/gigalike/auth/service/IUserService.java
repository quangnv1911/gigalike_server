package com.gigalike.auth.service;

import com.gigalike.auth.dto.data.UserDto;
import com.gigalike.auth.dto.request.UpdateUserAmount;
import com.gigalike.auth.dto.request.UpdateUserRequest;
import com.gigalike.auth.dto.request.UpdateUserTokenRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface IUserService {
    UserDto getCurrentUser();
    UserDto getUserById(UUID userId);
    void updateUserInfo(UUID userId, UpdateUserRequest updateUserRequest);
    void updateUserAmount(String userName, UpdateUserAmount updateUserAmount);
    void updateUserIpValid(UUID userId, String ip);
    void updateUserToken(UpdateUserTokenRequest updateUserTokenRequest);
    String getUserToken(UUID userId);
}
