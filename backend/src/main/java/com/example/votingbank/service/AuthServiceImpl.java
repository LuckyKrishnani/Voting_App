package com.example.votingbank.service;

import com.example.votingbank.dto.AuthRequest;
import com.example.votingbank.dto.AuthResponse;
import com.example.votingbank.model.Roles;
import com.example.votingbank.model.Users;
import com.example.votingbank.repository.RoleRepository;
import com.example.votingbank.repository.UserRepository;
import com.example.votingbank.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        Users user = userRepository.findByUsername(request.getUsernameOrEmail())
                .orElse(userRepository.findByEmail(request.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid credentials")));

        if (!HashUtil.sha256(request.getPassword()).equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Return a simple token (user ID) for testing without JWT
        String token = "simple-token-" + user.getId().toString();
        return new AuthResponse(token, user.getUsername(), user.getRole().getName());
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsernameOrEmail()).isPresent() ||
            userRepository.findByEmail(request.getUsernameOrEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Roles userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            throw new RuntimeException("Role not found");
        }

        Users user = new Users();
        user.setUsername(request.getUsernameOrEmail());
        user.setEmail(request.getUsernameOrEmail());
        user.setPasswordHash(HashUtil.sha256(request.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);

        // Return a simple token (user ID) for testing without JWT
        String token = "simple-token-" + user.getId().toString();
        return new AuthResponse(token, user.getUsername(), user.getRole().getName());
    }
}
