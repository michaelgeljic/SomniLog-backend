package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // We can define custom methods here like findbyid, findall, save, delete...
    User findByEmail(String email);
    User findByUsername(String username);
}
