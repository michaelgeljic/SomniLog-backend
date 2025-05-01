package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.KeywordVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordVariationRepository extends JpaRepository<KeywordVariation, Long> {
    // No extra methods needed for now, we just need findAll()
}