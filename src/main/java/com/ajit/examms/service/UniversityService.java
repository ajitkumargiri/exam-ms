package com.ajit.examms.service;

import com.ajit.examms.domain.University;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link University}.
 */
public interface UniversityService {
    /**
     * Save a university.
     *
     * @param university the entity to save.
     * @return the persisted entity.
     */
    University save(University university);

    /**
     * Partially updates a university.
     *
     * @param university the entity to update partially.
     * @return the persisted entity.
     */
    Optional<University> partialUpdate(University university);

    /**
     * Get all the universities.
     *
     * @return the list of entities.
     */
    List<University> findAll();

    /**
     * Get the "id" university.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<University> findOne(Long id);

    /**
     * Delete the "id" university.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
