package com.example.votingbank.repository;

import com.example.votingbank.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ElectionRepository extends JpaRepository<Election, UUID> {
    List<Election> findByIsPublishedTrue();
    List<Election> findByIsClosedFalse();
}
