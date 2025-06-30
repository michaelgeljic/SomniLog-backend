package com.rit.somnilog.backend.controller;

import com.rit.somnilog.backend.entity.DreamFact;
import com.rit.somnilog.backend.service.DreamFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for serving random dream-related facts.
 */
@RestController
@RequestMapping("/api/facts")
public class DreamFactController {

    private final DreamFactService factService;

    /**
     * Constructs the controller with the required service.
     *
     * @param factService the service used to retrieve dream facts
     */
    @Autowired
    public DreamFactController(DreamFactService factService) {
        this.factService = factService;
    }

    /**
     * Retrieves a single random dream fact.
     *
     * @return a {@link ResponseEntity} containing the random {@link DreamFact}
     */
    @GetMapping("/random")
    public ResponseEntity<DreamFact> getRandomFact() {
        DreamFact fact = factService.getRandomFact();
        return ResponseEntity.ok(fact);
    }
}
