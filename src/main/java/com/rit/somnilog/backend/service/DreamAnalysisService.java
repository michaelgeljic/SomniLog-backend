package com.rit.somnilog.backend.service;

import com.rit.somnilog.backend.entity.DreamBook;
import com.rit.somnilog.backend.entity.KeywordVariation;
import com.rit.somnilog.backend.repository.DreamBookRepository;
import com.rit.somnilog.backend.repository.KeywordVariationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DreamAnalysisService {

    private final DreamBookRepository dreamBookRepository;
    private final KeywordVariationRepository keywordVariationRepository;

    public DreamAnalysisService(DreamBookRepository dreamBookRepository, KeywordVariationRepository keywordVariationRepository) {
        this.dreamBookRepository = dreamBookRepository;
        this.keywordVariationRepository = keywordVariationRepository;
    }

    public List<DreamBook> analyzeDreamContent(String content) {
        List<DreamBook> matchedSymbols = new ArrayList<>();
        String lowerContent = content.toLowerCase();

        // Load all keyword variations
        List<KeywordVariation> variations = keywordVariationRepository.findAll();

        for (KeywordVariation variation : variations) {
            if (lowerContent.contains(variation.getVariation().toLowerCase())) {
                DreamBook symbol = variation.getDreamBook();
                if (!matchedSymbols.contains(symbol)) {
                    matchedSymbols.add(symbol);
                }
            }
        }

        return matchedSymbols;
    }
}