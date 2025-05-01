package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.Dream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DreamRepository extends JpaRepository<Dream, Long> {
    List<Dream> findByUserId(Long userId);
}