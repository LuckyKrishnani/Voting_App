package com.example.votingbank.dto;

import lombok.*;

import java.util.UUID;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private UUID id;
    private String ownerType;
    private UUID ownerId;
    private String fileUrl;
    private String mimeType;
    private UUID uploadedBy;
    private OffsetDateTime uploadedAt;
}
