package com.gigalike.auth.service.impl;

import com.gigalike.auth.dto.data.UserDto;
import com.gigalike.auth.dto.request.UpdateUserRequest;
import com.gigalike.auth.entity.User;
import com.gigalike.auth.repository.UserRepository;
import com.gigalike.auth.service.IUserService;
import com.gigalike.shared.exception.NotFoundException;
import com.gigalike.shared.exception.UserNotFoundException;
import com.gigalike.shared.util.CommonUtil;
import com.gigalike.shared.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;

    @Override
    public UserDto getCurrentUser() {
        return findUserByUsername(SecurityUtil.getCurrentUsername());
    }

    @Override
    public UserDto getUserById(UUID userId) {
        var user = findUserById(userId);
        return UserDto.fromUser(user);
    }

    @Override
    public void updateUserInfo(UUID userId, UpdateUserRequest updateUserRequest) {
        var user = findUserById(userId);
        user.setAvatar(updateUserRequest.getAvatar());
        user.setLastName(updateUserRequest.getLastName());
        user.setEnabled(updateUserRequest.getIsEnabled());
        user.setFirstName(updateUserRequest.getFirstName());
        userRepository.save(user);
        if (!Objects.equals(updateUserRequest.getIp(), "")) {
            updateUserIpValid(userId, updateUserRequest.getIp());
        }

    }

    @Override
    public void updateUserIpValid(UUID userId, String ip) {
        var user = findUserById(userId);
        List<String> currentUserIp = CommonUtil.convertStringArrayToList(user.getIpValid());
        List<String> newUserIp = CommonUtil.convertStringArrayToList(ip);
        Set<String> mergedSet = new HashSet<>(currentUserIp);
        mergedSet.addAll(newUserIp);
        user.setIpValid(String.join(",", mergedSet));
    }

    private User findUserById(UUID userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.info("Update user ip fail, cannot find user");
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    private UserDto findUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User %s not found", username));
        }
        return UserDto.fromUser(user.get());
    }
}
