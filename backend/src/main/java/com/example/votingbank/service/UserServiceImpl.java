package com.example.votingbank.service;

import com.example.votingbank.dto.UserProfileResponse;
import com.example.votingbank.dto.UserResponse;
import com.example.votingbank.model.UserProfile;
import com.example.votingbank.model.Users;
import com.example.votingbank.repository.UserProfileRepository;
import com.example.votingbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Users getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserResponse getUserResponseById(UUID id) {
        Users user = getUserById(id);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileResponse getUserProfile(UUID userId) {
        UserProfile profile = userProfileRepository.findById(userId).orElseThrow();
        return new UserProfileResponse(profile.getFullName(), profile.getRollNo(), profile.getDepartment(), profile.getPhotoUrl(), profile.getExtra());
    }

    @Override
    public void updateUserProfile(UUID userId, UserProfileResponse profileDto) {
        UserProfile profile = userProfileRepository.findById(userId).orElseThrow();
        profile.setFullName(profileDto.getFullName());
        profile.setRollNo(profileDto.getRollNo());
        profile.setDepartment(profileDto.getDepartment());
        profile.setPhotoUrl(profileDto.getPhotoUrl());
        profile.setExtra(profileDto.getExtra());
        userProfileRepository.save(profile);
    }
}
