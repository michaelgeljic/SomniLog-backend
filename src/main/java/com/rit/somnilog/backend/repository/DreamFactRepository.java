package com.rit.somnilog.backend.repository;

import com.rit.somnilog.backend.entity.DreamFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for accessing {@link DreamFact} entities from the database.
 * <p>
 * Provides basic CRUD operations and a custom query for retrieving a random dream fact.
 */
public interface DreamFactRepository extends JpaRepository<DreamFact, Long> {

    /**
     * Retrieves a single random dream fact from the database.
     * <p>
     * This uses a native SQL query with ORDER BY RAND() which is simple but not efficient for large datasets.
     *
     * @return a randomly selected {@link DreamFact}
     */
    @Query(value = "SELECT * FROM dream_facts ORDER BY RAND() LIMIT 1", nativeQuery = true)
    DreamFact getRandomFact();
}
