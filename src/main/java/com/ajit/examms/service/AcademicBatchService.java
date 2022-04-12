package com.ajit.examms.service;

import com.ajit.examms.domain.AcademicBatch;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AcademicBatch}.
 */
public interface AcademicBatchService {
    /**
     * Save a academicBatch.
     *
     * @param academicBatch the entity to save.
     * @return the persisted entity.
     */
    AcademicBatch save(AcademicBatch academicBatch);

    /**
     * Partially updates a academicBatch.
     *
     * @param academicBatch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AcademicBatch> partialUpdate(AcademicBatch academicBatch);

    /**
     * Get all the academicBatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcademicBatch> findAll(Pageable pageable);

    /**
     * Get the "id" academicBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicBatch> findOne(Long id);

    /**
     * Delete the "id" academicBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
