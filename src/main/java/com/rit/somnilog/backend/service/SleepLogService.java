package com.rit.somnilog.backend.service;

import com.rit.somnilog.backend.entity.SleepLog;
import com.rit.somnilog.backend.repository.SleepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing sleep logs.
 * Provides methods to create, retrieve, and delete sleep log entries.
 */
@Service
public class SleepLogService {

    private final SleepLogRepository sleepLogRepository;

    /**
     * Constructs the SleepLogService with the specified repository.
     *
     * @param sleepLogRepository repository for managing sleep logs
     */
    @Autowired
    public SleepLogService(SleepLogRepository sleepLogRepository) {
        this.sleepLogRepository = sleepLogRepository;
    }

    /**
     * Saves a new or updated sleep log to the database.
     *
     * @param log the sleep log to save
     * @return the saved SleepLog object
     */
    public SleepLog save(SleepLog log) {
        return sleepLogRepository.save(log);
    }

    /**
     * Retrieves all sleep logs for a specific user, sorted by sleep start time descending.
     *
     * @param userId the ID of the user
     * @return a list of SleepLog entries for the user
     */
    public List<SleepLog> getLogsByUserId(Long userId) {
        return sleepLogRepository.findByUserIdOrderBySleepStartDesc(userId);
    }

    /**
     * Retrieves a sleep log by its ID.
     *
     * @param id the ID of the sleep log
     * @return the SleepLog object if found, or null if not found
     */
    public SleepLog getById(Long id) {
        return sleepLogRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a sleep log from the database.
     *
     * @param log the sleep log to delete
     */
    public void delete(SleepLog log) {
        sleepLogRepository.delete(log);
    }

}
