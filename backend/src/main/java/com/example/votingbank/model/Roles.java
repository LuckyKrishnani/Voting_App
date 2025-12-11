package com.example.votingbank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Roles {
    @Id
    private Short id; // SMALLINT in DB

    @Column(unique = true, nullable = false)
    private String name;
}
