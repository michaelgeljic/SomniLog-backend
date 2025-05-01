package com.rit.somnilog.backend.service;

import com.rit.somnilog.backend.entity.*;
import com.rit.somnilog.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DreamService {

    private final KeywordVariationRepository keywordVariationRepository;
    private final DreamSymbolRepository dreamSymbolRepository;
    private final TagRepository tagRepository;

    public DreamService(KeywordVariationRepository keywordVariationRepository,
                        DreamSymbolRepository dreamSymbolRepository,
                        TagRepository tagRepository) {
        this.keywordVariationRepository = keywordVariationRepository;
        this.dreamSymbolRepository = dreamSymbolRepository;
        this.tagRepository = tagRepository;
    }

    public void analyzeDreamSymbols(Dream dream) {
        String content = dream.getContent().toLowerCase();
        List<KeywordVariation> variations = keywordVariationRepository.findAll();

        for (KeywordVariation variation : variations) {
            if (content.contains(variation.getVariation().toLowerCase())) {
                DreamBook dreamBook = variation.getDreamBook();
                DreamSymbol dreamSymbol = new DreamSymbol(dream, dreamBook);
                dreamSymbolRepository.save(dreamSymbol);
            }
        }
    }

    public void attachTagsToDream(Dream dream, List<String> tagNames) {
        Set<Tag> tagSet = new HashSet<>();

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag(tagName);
                tag = tagRepository.save(tag);
            }
            tagSet.add(tag);
        }

        dream.setTags(new ArrayList<>(tagSet));
    }
}