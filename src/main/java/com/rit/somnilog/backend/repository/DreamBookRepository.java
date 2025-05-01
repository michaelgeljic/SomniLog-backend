package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.DreamBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DreamBookRepository extends JpaRepository<DreamBook, Long> {
    DreamBook findByWord(String word);
}