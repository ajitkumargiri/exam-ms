package com.ajit.examms.service;

import com.ajit.examms.domain.ExamApplicationForm;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ExamApplicationForm}.
 */
public interface ExamApplicationFormService {
    /**
     * Save a examApplicationForm.
     *
     * @param examApplicationForm the entity to save.
     * @return the persisted entity.
     */
    ExamApplicationForm save(ExamApplicationForm examApplicationForm);

    /**
     * Partially updates a examApplicationForm.
     *
     * @param examApplicationForm the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamApplicationForm> partialUpdate(ExamApplicationForm examApplicationForm);

    /**
     * Get all the examApplicationForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamApplicationForm> findAll(Pageable pageable);

    /**
     * Get the "id" examApplicationForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamApplicationForm> findOne(Long id);

    /**
     * Delete the "id" examApplicationForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
