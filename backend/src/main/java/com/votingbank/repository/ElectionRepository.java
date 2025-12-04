package com.votingbank.repository;

import com.votingbank.entity.Election;
import com.votingbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Election entity operations.
 */
@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    /**
     * Find elections by status.
     * @param status the election status
     * @return list of elections with the given status
     */
    List<Election> findByStatus(Election.ElectionStatus status);

    /**
     * Find elections created by a specific user.
     * @param createdBy the user who created the elections
     * @return list of elections created by the user
     */
    List<Election> findByCreatedBy(User createdBy);

    /**
     * Find active elections (status = ACTIVE and current time between start and end time).
     * @param now the current time
     * @return list of active elections
     */
    @Query("SELECT e FROM Election e WHERE e.status = 'ACTIVE' AND :now BETWEEN e.startTime AND e.endTime")
    List<Election> findActiveElections(@Param("now") LocalDateTime now);

    /**
     * Find elections by status and created by user.
     * @param status the election status
     * @param createdBy the user who created the elections
     * @return list of elections
     */
    List<Election> findByStatusAndCreatedBy(Election.ElectionStatus status, User createdBy);
}
