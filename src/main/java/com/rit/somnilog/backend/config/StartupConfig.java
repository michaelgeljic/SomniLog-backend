package com.rit.somnilog.backend.config;

import com.rit.somnilog.backend.entity.Emotion;
import com.rit.somnilog.backend.repository.EmotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Bean
    public CommandLineRunner loadDefaultEmotions(EmotionRepository emotionRepository) {
        return args -> {
            String[] defaultEmotions = {"Happy", "Anxious", "Neutral", "Scared", "Excited"};

            for (String emotionName : defaultEmotions) {
                // Only add if it doesn't exist as a built-in emotion
                if (emotionRepository.findByNameAndUserIsNull(emotionName).isEmpty()) {
                    emotionRepository.save(new Emotion(emotionName));
                }
            }
        };
    }
}