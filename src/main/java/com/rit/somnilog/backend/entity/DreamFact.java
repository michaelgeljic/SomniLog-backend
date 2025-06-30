package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;
/**
 * Represents an interesting or educational fact about dreams.
 * Stored as plain text.
 */

@Entity
@Table(name = "dream_facts")
public class DreamFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String fact;

    public DreamFact() {}

    public DreamFact(String fact) {
        this.fact = fact;
    }

    public Long getId() {
        return id;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }
}
