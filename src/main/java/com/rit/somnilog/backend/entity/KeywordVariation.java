package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "keyword_variations")
public class KeywordVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String variation;

    @ManyToOne
    @JoinColumn(name = "dream_book_id", nullable = false)
    @JsonIgnore // prevents infinite recursion when serializing dream books
    private DreamBook dreamBook;

    // Constructors
    public KeywordVariation() {}

    public KeywordVariation(String variation, DreamBook dreamBook) {
        this.variation = variation;
        this.dreamBook = dreamBook;
    }

    // Getters and setters
    public Long getId() { return id; }

    public String getVariation() { return variation; }
    public void setVariation(String variation) { this.variation = variation; }

    public DreamBook getDreamBook() { return dreamBook; }
    public void setDreamBook(DreamBook dreamBook) { this.dreamBook = dreamBook; }
}