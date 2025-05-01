package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.entity.LoginLog;
import com.rit.somnilog.backend.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login-logs")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginLogController {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // ✅ Only admins can access this
    public ResponseEntity<List<LoginLog>> getAllLoginLogs() {
        return ResponseEntity.ok(loginLogRepository.findAll());
    }
}