package com.example.votingbank.service;

import com.example.votingbank.dto.AuthRequest;
import com.example.votingbank.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(AuthRequest request);
}
