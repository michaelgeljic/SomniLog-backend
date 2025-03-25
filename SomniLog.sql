USE SomniLog;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, email, password_hash)
VALUES ('helena123', 'helena@example.com', 'hashedpasswordhere');

DELETE FROM users WHERE username = 'helena123';
SELECT * FROM users WHERE username = 'helena123';