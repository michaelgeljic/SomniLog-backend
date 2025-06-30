package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.SleepLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link SleepLog} entities.
 * <p>
 * Provides standard CRUD operations and a custom query to retrieve sleep logs
 * for a user, ordered by the start time of sleep in descending order.
 */
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {

    /**
     * Retrieves all sleep logs for a given user, ordered by sleep start time (most recent first).
     *
     * @param userId the ID of the user
     * @return a list of {@link SleepLog} entries for the user
     */
    List<SleepLog> findByUserIdOrderBySleepStartDesc(Long userId);
}
