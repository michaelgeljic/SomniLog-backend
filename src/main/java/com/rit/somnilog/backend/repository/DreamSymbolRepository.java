package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.DreamSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DreamSymbolRepository extends JpaRepository<DreamSymbol, Long> {
}