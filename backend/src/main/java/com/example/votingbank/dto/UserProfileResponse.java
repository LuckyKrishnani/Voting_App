package com.example.votingbank.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String fullName;
    private String rollNo;
    private String department;
    private String photoUrl;
    private String extra; // JSON as String
}
