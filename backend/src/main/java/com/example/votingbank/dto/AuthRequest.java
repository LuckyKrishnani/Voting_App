package com.example.votingbank.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String usernameOrEmail;
    private String password;
}
