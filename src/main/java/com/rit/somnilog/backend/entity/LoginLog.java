package com.rit.somnilog.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loginTime;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference // Prevent recursive serialization
    private User user;

    public LoginLog(LocalDateTime loginTime, String ipAddress, User user) {
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.user = user;
    }
}