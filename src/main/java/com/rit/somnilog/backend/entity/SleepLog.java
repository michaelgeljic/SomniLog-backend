package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 * Represents a user's sleep session log.
 * Includes start/end times, sleep quality, optional notes, and associated user.
 */

@Entity
@Table(name = "sleep_logs")
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sleep_start", nullable = false)
    private LocalDateTime sleepStart;

    @Column(name = "sleep_end", nullable = false)
    private LocalDateTime sleepEnd;

    @Column(name = "sleep_quality")
    private String sleepQuality;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public Long getId() { return id; }

    public LocalDateTime getSleepStart() { return sleepStart; }
    public void setSleepStart(LocalDateTime sleepStart) { this.sleepStart = sleepStart; }

    public LocalDateTime getSleepEnd() { return sleepEnd; }
    public void setSleepEnd(LocalDateTime sleepEnd) { this.sleepEnd = sleepEnd; }

    public String getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(String sleepQuality) { this.sleepQuality = sleepQuality; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
