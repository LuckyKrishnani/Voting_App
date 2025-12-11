package com.example.votingbank.controller;

import com.example.votingbank.dto.UserProfileResponse;
import com.example.votingbank.dto.UserResponse;
import com.example.votingbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserResponseById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable UUID id, @RequestBody UserProfileResponse profile) {
        userService.updateUserProfile(id, profile);
        return ResponseEntity.ok().build();
    }
}
