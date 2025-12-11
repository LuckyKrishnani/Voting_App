package com.example.votingbank.service;

import com.example.votingbank.dto.CreateElectionRequest;
import com.example.votingbank.dto.ElectionResponse;
import com.example.votingbank.model.Election;

import java.util.List;
import java.util.UUID;

public interface ElectionService {
    Election createElection(CreateElectionRequest request, UUID creatorId);
    List<ElectionResponse> getAllElections();
    Election getElectionById(UUID id);
    void closeElection(UUID id);
}
