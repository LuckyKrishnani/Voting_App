package com.example.votingbank.service;

import com.example.votingbank.dto.UserResponse;
import com.example.votingbank.dto.UserProfileResponse;
import com.example.votingbank.model.Users;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Users getUserById(UUID id);
    UserResponse getUserResponseById(UUID id);
    List<UserResponse> getAllUsers();
    UserProfileResponse getUserProfile(UUID userId);
    void updateUserProfile(UUID userId, UserProfileResponse profile);
}
