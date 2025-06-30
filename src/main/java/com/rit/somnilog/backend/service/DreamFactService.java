package com.rit.somnilog.backend.service;

import com.rit.somnilog.backend.entity.DreamFact;
import com.rit.somnilog.backend.repository.DreamFactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that provides functionality related to dream facts,
 * including retrieving a random fact from the database.
 */
@Service
public class DreamFactService {

    private final DreamFactRepository factRepository;

    /**
     * Constructs the DreamFactService with the specified repository.
     *
     * @param factRepository repository for accessing dream facts
     */
    @Autowired
    public DreamFactService(DreamFactRepository factRepository) {
        this.factRepository = factRepository;
    }

    /**
     * Retrieves a random dream fact from the repository.
     *
     * @return a random DreamFact object
     */
    public DreamFact getRandomFact() {
        return factRepository.getRandomFact();
    }
}
