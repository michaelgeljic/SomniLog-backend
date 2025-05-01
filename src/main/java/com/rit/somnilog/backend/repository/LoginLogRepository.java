package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}