package com.rit.somnilog.backend.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a user's login event.
 * Used for exposing login log data in a clean format for API responses.
 */
public class LoginLogDTO {

    /** Unique ID of the login log entry */
    public Long id;

    /** The timestamp of the login event */
    public LocalDateTime loginTime;

    /** The IP address from which the login was made */
    public String ipAddress;

    /** The username of the user who logged in */
    public String username;

    /**
     * Constructs a new LoginLogDTO.
     *
     * @param id         the log entry ID
     * @param loginTime  the timestamp of login
     * @param ipAddress  the IP address used during login
     * @param username   the username of the user
     */
    public LoginLogDTO(Long id, LocalDateTime loginTime, String ipAddress, String username) {
        this.id = id;
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.username = username;
    }
}
