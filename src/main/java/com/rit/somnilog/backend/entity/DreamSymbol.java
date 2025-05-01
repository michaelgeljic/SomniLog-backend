package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dream_symbols")
public class DreamSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dream_id", nullable = false)
    @JsonIgnore // ADD THIS
    private Dream dream;

    @ManyToOne
    @JoinColumn(name = "symbol_id", nullable = false)
    private DreamBook dreamBook;

    // Constructors
    public DreamSymbol() {}

    public DreamSymbol(Dream dream, DreamBook dreamBook) {
        this.dream = dream;
        this.dreamBook = dreamBook;
    }

    // Getters and setters
    public Long getId() { return id; }

    public Dream getDream() { return dream; }
    public void setDream(Dream dream) { this.dream = dream; }

    public DreamBook getDreamBook() { return dreamBook; }
    public void setDreamBook(DreamBook dreamBook) { this.dreamBook = dreamBook; }
}