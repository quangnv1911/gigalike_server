package com.gigalike.auth.controller;

import com.gigalike.auth.dto.request.UpdateUserAmount;
import com.gigalike.auth.dto.request.UpdateUserRequest;
import com.gigalike.auth.dto.request.UpdateUserTokenRequest;
import com.gigalike.auth.dto.response.AuthResponse;
import com.gigalike.auth.service.IUserService;
import com.gigalike.shared.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMe() {
        var user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Get current user successfully", user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable UUID userId) {
        var user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("Get user successfully", user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUserInfo(userId, updateUserRequest);
        return ResponseEntity.ok(ApiResponse.success("User account has been updated successfully", updateUserRequest));
    }

    @PutMapping("/amount/{userName}")
    public ResponseEntity<ApiResponse<?>> updateUserAmount(@PathVariable String userName, @RequestBody UpdateUserAmount updateUserAmount) {
        userService.updateUserAmount(userName, updateUserAmount);
        return ResponseEntity.ok(ApiResponse.success("User account has been updated successfully", updateUserAmount));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> registerCollaborator(@PathVariable UUID userId, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUserInfo(userId, updateUserRequest);
        return ResponseEntity.ok(ApiResponse.success("User account has been updated successfully", updateUserRequest));
    }
}
