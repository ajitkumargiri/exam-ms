package com.ajit.examms.service;

import com.ajit.examms.domain.College;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link College}.
 */
public interface CollegeService {
    /**
     * Save a college.
     *
     * @param college the entity to save.
     * @return the persisted entity.
     */
    College save(College college);

    /**
     * Partially updates a college.
     *
     * @param college the entity to update partially.
     * @return the persisted entity.
     */
    Optional<College> partialUpdate(College college);

    /**
     * Get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<College> findAll(Pageable pageable);

    /**
     * Get the "id" college.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<College> findOne(Long id);

    /**
     * Delete the "id" college.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
