package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emotions", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // null for built-in emotions

    public Emotion() {}

    // Constructor for user-defined emotions
    public Emotion(String name, User user) {
        this.name = name;
        this.user = user;
    }

    // Constructor for default emotions (no user)
    public Emotion(String name) {
        this.name = name;
        this.user = null;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}