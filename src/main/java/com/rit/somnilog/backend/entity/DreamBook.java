package com.rit.somnilog.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dream_book")
public class DreamBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String word;

    @Column(nullable = false)
    private String meaning;

    @OneToMany(mappedBy = "dreamBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeywordVariation> variations;

    public DreamBook() {}

    public DreamBook(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    // Getters and setters
    public Long getId() { return id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }

    public List<KeywordVariation> getVariations() { return variations; }
    public void setVariations(List<KeywordVariation> variations) { this.variations = variations; }
}