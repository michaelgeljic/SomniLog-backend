USE SomniLog;

DROP TABLE IF EXISTS keyword_variations;
DROP TABLE IF EXISTS dream_book;
DROP TABLE IF EXISTS dreams;
DROP TABLE IF EXISTS emotions;
DROP TABLE IF EXISTS users;

-- USERS TABLE VERSION
CREATE TABLE users (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- EMOTIONS TABLE (New)
CREATE TABLE emotions (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id INT UNSIGNED DEFAULT NULL,
    UNIQUE KEY unique_emotion_user (name, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- DREAMS TABLE
CREATE TABLE dreams (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    dream_date DATE NOT NULL,
    mood VARCHAR(50) NOT NULL,
    type_tag VARCHAR(50),
    is_recurring BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- DREAM BOOK TABLE
CREATE TABLE dream_book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    word VARCHAR(50) NOT NULL UNIQUE,
    meaning TEXT NOT NULL
);

-- INSERT INTO emotions (name) VALUES 
-- ('Happy'), 
-- ('Anxious'), 
-- ('Neutral'), 
-- ('Scared'), 
-- ('Excited');


SHOW COLUMNS FROM dreams;
SHOW COLUMNS FROM dream_book;
ALTER TABLE dream_book MODIFY id INT UNSIGNED AUTO_INCREMENT;
ALTER TABLE dream_book MODIFY COLUMN id INT UNSIGNED NOT NULL AUTO_INCREMENT;


CREATE TABLE dream_symbols (
    dream_id INT UNSIGNED NOT NULL,
    symbol_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (dream_id, symbol_id),
    FOREIGN KEY (dream_id) REFERENCES dreams(id) ON DELETE CASCADE,
    FOREIGN KEY (symbol_id) REFERENCES dream_book(id) ON DELETE CASCADE
);



-- KEYWORD VARIATIONS TABLE
CREATE TABLE keyword_variations (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    variation VARCHAR(100) NOT NULL UNIQUE,
    dream_book_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (dream_book_id) REFERENCES dream_book(id) ON DELETE CASCADE
);

-- Example insert
-- INSERT INTO users (username, email, password_hash)
-- VALUES ('helena123', 'helena@example.com', 'hashedpasswordhere');

-- Example cleanup
-- DELETE FROM users WHERE username = 'helena123';
-- SELECT * FROM users WHERE username = 'helena123';


-- POPULATING DREAM TABLES
INSERT INTO dream_book (word, meaning) VALUES
('cat', 'Symbol of independence, intuition, and femininity.'),
('teeth falling', 'Often linked to anxiety, fear of aging or appearance.'),
('snake', 'Represents transformation, healing, or hidden fears.'),
('flying', 'Freedom, escape from pressures, or spiritual elevation.'),
('falling', 'Insecurity, fear of failure, or loss of control.'),
('water', 'Emotions, subconscious, or cleansing.'),
('baby', 'New beginnings, vulnerability, or responsibilities.'),
('death', 'Endings, transitions, or unresolved issues.'),
('being chased', 'Avoidance of a problem or stress.'),
('school', 'Learning, anxiety, or past experiences.');

-- KEYWORD VARIATIONS POPULATION
INSERT INTO keyword_variations (variation, dream_book_id) VALUES
('cat', 1),
('cats', 1),
('my cat', 1),
('teeth falling', 2),
('tooth fell out', 2),
('teeth fell', 2),
('lost a tooth', 2),
('snake', 3),
('snakes', 3),
('bitten by a snake', 3),
('flying', 4),
('i was flying', 4),
('flew', 4),
('falling', 5),
('i fell', 5),
('fell', 5),
('water', 6),
('in water', 6),
('drowning', 6),
('baby', 7),
('babies', 7),
('holding a baby', 7),
('death', 8),
('died', 8),
('someone died', 8),
('being chased', 9),
('chased', 9),
('running away', 9),
('school', 10),
('at school', 10),
('in classroom', 10);