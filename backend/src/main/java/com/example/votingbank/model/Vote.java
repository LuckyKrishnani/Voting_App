package com.example.votingbank.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"election_id", "user_id"})
})
public class Vote {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreationTimestamp
    @Column(name = "cast_at", nullable = false, updatable = false)
    private OffsetDateTime castAt;

    @Column(name = "ballot_hash", nullable = false)
    private String ballotHash;

    @Column(name = "encrypted_ballot")
    private byte[] encryptedBallot;

    @Column(name = "is_counted")
    private Boolean isCounted = false;
}
