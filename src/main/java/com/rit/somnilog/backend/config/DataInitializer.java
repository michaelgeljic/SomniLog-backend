package com.rit.somnilog.backend.config;

import com.rit.somnilog.backend.entity.Emotion;
import com.rit.somnilog.backend.entity.Tag;
import com.rit.somnilog.backend.repository.EmotionRepository;
import com.rit.somnilog.backend.repository.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadEmotionsAndTags(EmotionRepository emotionRepository, TagRepository tagRepository) {
        return args -> {
            String[] defaultEmotions = {"Happy", "Sad", "Angry", "Excited", "Anxious", "Neutral", "Scared"};
            for (String emotion : defaultEmotions) {
                if (emotionRepository.findByNameAndUserIsNull(emotion).isEmpty()) {
                    emotionRepository.save(new Emotion(emotion));
                }
            }

            String[] defaultTags = {"Lucid", "Nightmare", "Recurring", "Fantasy", "Flying", "Water"};
            for (String tagName : defaultTags) {
                if (tagRepository.findByName(tagName) == null) {
                    tagRepository.save(new Tag(tagName));
                }
            }
        };
    }
}