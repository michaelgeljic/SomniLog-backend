package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.entity.SleepLog;
import com.rit.somnilog.backend.entity.User;
import com.rit.somnilog.backend.repository.UserRepository;
import com.rit.somnilog.backend.service.SleepLogService;
import com.rit.somnilog.backend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing sleep logs for authenticated users.
 * Provides endpoints to create, view, update, and delete sleep entries.
 */
@RestController
@RequestMapping("/api/sleep")
public class SleepLogController {

    @Autowired
    private SleepLogService sleepLogService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Creates a new sleep log for the authenticated user.
     *
     * @param log the sleep log to save
     * @param authHeader the Authorization header containing the JWT token
     * @return the saved {@link SleepLog}
     */
    @PostMapping
    public ResponseEntity<SleepLog> createLog(
            @RequestBody SleepLog log,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);
        log.setUser(user);
        return ResponseEntity.ok(sleepLogService.save(log));
    }

    /**
     * Retrieves all sleep logs for the authenticated user, ordered by start time (most recent first).
     *
     * @param authHeader the Authorization header containing the JWT token
     * @return a list of {@link SleepLog} entries
     */
    @GetMapping
    public ResponseEntity<List<SleepLog>> getLogs(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok(sleepLogService.getLogsByUserId(user.getId()));
    }

    /**
     * Updates an existing sleep log entry for the authenticated user.
     *
     * @param id the ID of the sleep log to update
     * @param updatedLog the updated sleep log values
     * @param authHeader the Authorization header containing the JWT token
     * @return the updated {@link SleepLog}, or 403 if not authorized
     */
    @PutMapping("/{id}")
    public ResponseEntity<SleepLog> updateLog(
            @PathVariable Long id,
            @RequestBody SleepLog updatedLog,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        SleepLog existingLog = sleepLogService.getById(id);
        if (existingLog == null || !existingLog.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        existingLog.setSleepStart(updatedLog.getSleepStart());
        existingLog.setSleepEnd(updatedLog.getSleepEnd());
        existingLog.setSleepQuality(updatedLog.getSleepQuality());
        existingLog.setNotes(updatedLog.getNotes());

        return ResponseEntity.ok(sleepLogService.save(existingLog));
    }

    /**
     * Deletes a sleep log entry if it belongs to the authenticated user.
     *
     * @param id the ID of the sleep log to delete
     * @param authHeader the Authorization header containing the JWT token
     * @return 204 No Content if successful, or 403 if unauthorized
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        SleepLog log = sleepLogService.getById(id);
        if (log == null || !log.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        sleepLogService.delete(log);
        return ResponseEntity.noContent().build();
    }
}
