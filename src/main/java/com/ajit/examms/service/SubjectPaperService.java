package com.ajit.examms.service;

import com.ajit.examms.domain.SubjectPaper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SubjectPaper}.
 */
public interface SubjectPaperService {
    /**
     * Save a subjectPaper.
     *
     * @param subjectPaper the entity to save.
     * @return the persisted entity.
     */
    SubjectPaper save(SubjectPaper subjectPaper);

    /**
     * Partially updates a subjectPaper.
     *
     * @param subjectPaper the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubjectPaper> partialUpdate(SubjectPaper subjectPaper);

    /**
     * Get all the subjectPapers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubjectPaper> findAll(Pageable pageable);

    /**
     * Get the "id" subjectPaper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectPaper> findOne(Long id);

    /**
     * Delete the "id" subjectPaper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
