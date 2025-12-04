package com.votingbank.service;

import com.votingbank.entity.Election;
import com.votingbank.entity.User;
import com.votingbank.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for election-related operations.
 */
@Service
@Transactional
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    /**
     * Create a new election.
     * @param election the election to create
     * @return the created election
     */
    public Election createElection(Election election) {
        election.setStatus(Election.ElectionStatus.DRAFT);
        return electionRepository.save(election);
    }

    /**
     * Find election by id.
     * @param id the election id
     * @return optional election
     */
    public Optional<Election> findById(Long id) {
        return electionRepository.findById(id);
    }

    /**
     * Get all elections.
     * @return list of elections
     */
    public List<Election> findAll() {
        return electionRepository.findAll();
    }

    /**
     * Get active elections.
     * @return list of active elections
     */
    public List<Election> findActiveElections() {
        return electionRepository.findActiveElections(LocalDateTime.now());
    }

    /**
     * Get elections by status.
     * @param status the election status
     * @return list of elections
     */
    public List<Election> findByStatus(Election.ElectionStatus status) {
        return electionRepository.findByStatus(status);
    }

    /**
     * Get elections created by user.
     * @param createdBy the user
     * @return list of elections
     */
    public List<Election> findByCreatedBy(User createdBy) {
        return electionRepository.findByCreatedBy(createdBy);
    }

    /**
     * Update election.
     * @param election the election to update
     * @return the updated election
     */
    public Election updateElection(Election election) {
        return electionRepository.save(election);
    }

    /**
     * Close election.
     * @param election the election to close
     * @return the closed election
     */
    public Election closeElection(Election election) {
        election.setStatus(Election.ElectionStatus.CLOSED);
        return electionRepository.save(election);
    }

    /**
     * Delete election by id.
     * @param id the election id
     */
    public void deleteElection(Long id) {
        electionRepository.deleteById(id);
    }
}
