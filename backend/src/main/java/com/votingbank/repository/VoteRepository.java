package com.votingbank.repository;

import com.votingbank.entity.Election;
import com.votingbank.entity.User;
import com.votingbank.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vote entity operations.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Find votes by election.
     * @param election the election
     * @return list of votes for the election
     */
    List<Vote> findByElection(Election election);

    /**
     * Find votes by voter.
     * @param voter the voter
     * @return list of votes by the voter
     */
    List<Vote> findByVoter(User voter);

    /**
     * Find vote by election and voter.
     * @param election the election
     * @param voter the voter
     * @return optional vote
     */
    Optional<Vote> findByElectionAndVoter(Election election, User voter);

    /**
     * Check if a user has already voted in an election.
     * @param election the election
     * @param voter the voter
     * @return true if voted, false otherwise
     */
    boolean existsByElectionAndVoter(Election election, User voter);

    /**
     * Count votes for a candidate in an election.
     * @param election the election
     * @param candidateId the candidate id
     * @return vote count
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.election = :election AND v.candidate.id = :candidateId")
    long countByElectionAndCandidateId(@Param("election") Election election, @Param("candidateId") Long candidateId);

    /**
     * Get vote counts for all candidates in an election.
     * @param election the election
     * @return list of vote counts per candidate
     */
    @Query("SELECT v.candidate.id, COUNT(v) FROM Vote v WHERE v.election = :election GROUP BY v.candidate.id")
    List<Object[]> getVoteCountsByElection(@Param("election") Election election);
}
