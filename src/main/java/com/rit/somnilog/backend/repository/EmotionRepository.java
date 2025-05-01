package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.Emotion;
import com.rit.somnilog.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    List<Emotion> findByUserIsNull(); // built-in emotions
    List<Emotion> findByUser(User user); // user-specific emotions

    Optional<Emotion> findByNameAndUserIsNull(String name);
    List<Emotion> findByUserIsNullOrUser(User user);
    boolean existsByNameAndUser(String name, User user);
}