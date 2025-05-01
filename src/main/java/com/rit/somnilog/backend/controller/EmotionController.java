package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.entity.Emotion;
import com.rit.somnilog.backend.entity.User;
import com.rit.somnilog.backend.repository.EmotionRepository;
import com.rit.somnilog.backend.repository.UserRepository;
import com.rit.somnilog.backend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/emotions")
public class EmotionController {

    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public EmotionController(EmotionRepository emotionRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.emotionRepository = emotionRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getEmotions(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        List<Emotion> builtIn = emotionRepository.findByUserIsNull();
        List<Emotion> userDefined = emotionRepository.findByUser(user);

        List<String> emotionNames = new ArrayList<>();
        builtIn.forEach(e -> emotionNames.add(e.getName()));
        userDefined.forEach(e -> emotionNames.add(e.getName()));

        return ResponseEntity.ok(emotionNames);
    }


    @PostMapping
    public ResponseEntity<?> addEmotion(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> request) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        String name = request.get("name");
        if (emotionRepository.existsByNameAndUser(name, user)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Emotion already exists."));
        }

        Emotion emotion = new Emotion(name, user);
        emotionRepository.save(emotion);

        return ResponseEntity.ok(Map.of("message", "Emotion added successfully."));
    }
}