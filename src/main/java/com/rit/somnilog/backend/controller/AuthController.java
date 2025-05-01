package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.entity.LoginLog;
import com.rit.somnilog.backend.entity.User;
import com.rit.somnilog.backend.repository.LoginLogRepository;
import com.rit.somnilog.backend.repository.UserRepository;
import com.rit.somnilog.backend.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginLogRepository loginLogRepository;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, LoginLogRepository loginLogRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.loginLogRepository = loginLogRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists."));
        }

        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null || !passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
        }

        String token = jwtUtil.generateToken(user); // ✅ now passes the whole user

        return ResponseEntity.ok(Map.of(
                "message", "Login successful!",
                "token", token
        ));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> passwords) {

        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found."));
        }

        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            return ResponseEntity.status(403).body(Map.of("message", "Current password is incorrect."));
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password changed successfully!"));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username);

        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.ok(Map.of("message", "Account deleted successfully."));
        }

        return ResponseEntity.status(404).body(Map.of("message", "User not found."));
    }

    // ================================
    // ADMIN: View all login logs
    // ================================
    @GetMapping("/all-login-logs")
    public ResponseEntity<?> getAllLoginLogs(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username);

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return ResponseEntity.status(403).body(Map.of("message", "Access denied."));
        }

        List<LoginLog> allLogs = loginLogRepository.findAll();
        return ResponseEntity.ok(allLogs);
    }
}