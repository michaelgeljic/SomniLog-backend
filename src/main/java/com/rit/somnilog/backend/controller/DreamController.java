package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.dto.DreamRequest;
import com.rit.somnilog.backend.entity.Dream;
import com.rit.somnilog.backend.entity.User;
import com.rit.somnilog.backend.repository.DreamRepository;
import com.rit.somnilog.backend.repository.UserRepository;
import com.rit.somnilog.backend.security.JwtUtil;
import com.rit.somnilog.backend.service.DreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dreams")
@CrossOrigin(origins = "http://localhost:3000")
public class DreamController {

    @Autowired
    private DreamRepository dreamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DreamService dreamService;

    @GetMapping
    public ResponseEntity<List<Dream>> getUserDreams(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        List<Dream> dreams = dreamRepository.findByUserId(user.getId());
        return ResponseEntity.ok(dreams);
    }

    @PostMapping
    public ResponseEntity<Dream> createDream(@RequestBody DreamRequest dreamRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        Dream dream = new Dream();
        dream.setUser(user);
        dream.setTitle(dreamRequest.getTitle());
        dream.setContent(dreamRequest.getContent());
        dream.setDreamDate(LocalDate.parse(dreamRequest.getDreamDate())); // <<< fix here
        dream.setMood(dreamRequest.getMood());
        dream.setTypeTag(dreamRequest.getTypeTag());
        dream.setIsRecurring(dreamRequest.getIsRecurring());

        Dream savedDream = dreamRepository.save(dream);

        dreamService.analyzeDreamSymbols(savedDream);

        if (dreamRequest.getTags() != null && !dreamRequest.getTags().isEmpty()) {
            dreamService.attachTagsToDream(savedDream, dreamRequest.getTags());
            dreamRepository.save(savedDream);
        }

        return ResponseEntity.ok(savedDream);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDream(
            @PathVariable Long id,
            @RequestBody Dream updatedDream,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }

        Dream existingDream = dreamRepository.findById(id).orElse(null);
        if (existingDream == null || !existingDream.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("message", "Dream not found or access denied"));
        }

        existingDream.setTitle(updatedDream.getTitle());
        existingDream.setContent(updatedDream.getContent());
        existingDream.setDreamDate(updatedDream.getDreamDate());
        existingDream.setMood(updatedDream.getMood());
        existingDream.setTypeTag(updatedDream.getTypeTag());
        existingDream.setIsRecurring(updatedDream.getIsRecurring());

        dreamRepository.save(existingDream);

        return ResponseEntity.ok(existingDream);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDream(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        Dream dream = dreamRepository.findById(id).orElse(null);
        if (dream == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Dream not found."));
        }

        if (!dream.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("message", "You are not authorized to delete this dream."));
        }

        dreamRepository.delete(dream);
        return ResponseEntity.ok(Map.of("message", "Dream deleted successfully."));
    }

    @GetMapping("/{id}/symbols")
    public ResponseEntity<?> getDreamSymbols(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        Dream dream = dreamRepository.findById(id).orElse(null);
        if (dream == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Dream not found."));
        }

        if (!dream.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("message", "You are not authorized to view these symbols."));
        }

        List<String> uniqueSymbols = dream.getDreamSymbols()
                .stream()
                .map(ds -> ds.getDreamBook().getWord())
                .distinct()
                .toList();

        return ResponseEntity.ok(uniqueSymbols);
    }
}