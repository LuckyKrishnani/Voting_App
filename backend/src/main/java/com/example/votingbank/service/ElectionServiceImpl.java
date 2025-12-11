package com.example.votingbank.service;

import com.example.votingbank.dto.CreateElectionRequest;
import com.example.votingbank.dto.ElectionResponse;
import com.example.votingbank.model.Election;
import com.example.votingbank.model.Users;
import com.example.votingbank.repository.ElectionRepository;
import com.example.votingbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final UserRepository userRepository;

    @Override
    public Election createElection(CreateElectionRequest request, UUID creatorId) {
        Users creator = userRepository.findById(creatorId).orElseThrow();
        Election election = new Election();
        election.setTitle(request.getTitle());
        election.setDescription(request.getDescription());
        election.setStartAt(request.getStartAt());
        election.setEndAt(request.getEndAt());
        election.setIsPublished(request.getIsPublished());
        election.setCreatedBy(creator);
        return electionRepository.save(election);
    }

    @Override
    public List<ElectionResponse> getAllElections() {
        return electionRepository.findAll().stream()
                .map(e -> new ElectionResponse(e.getId(), e.getTitle(), e.getDescription(), e.getStartAt(), e.getEndAt(), e.getIsPublished(), e.getIsClosed()))
                .collect(Collectors.toList());
    }

    @Override
    public Election getElectionById(UUID id) {
        return electionRepository.findById(id).orElseThrow();
    }

    @Override
    public void closeElection(UUID id) {
        Election election = getElectionById(id);
        election.setIsClosed(true);
        electionRepository.save(election);
    }
}
