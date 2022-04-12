package com.ajit.examms.service;

import com.ajit.examms.domain.ExamCenter;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ExamCenter}.
 */
public interface ExamCenterService {
    /**
     * Save a examCenter.
     *
     * @param examCenter the entity to save.
     * @return the persisted entity.
     */
    ExamCenter save(ExamCenter examCenter);

    /**
     * Partially updates a examCenter.
     *
     * @param examCenter the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamCenter> partialUpdate(ExamCenter examCenter);

    /**
     * Get all the examCenters.
     *
     * @return the list of entities.
     */
    List<ExamCenter> findAll();

    /**
     * Get the "id" examCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamCenter> findOne(Long id);

    /**
     * Delete the "id" examCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
