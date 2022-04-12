package com.ajit.examms.service;

import com.ajit.examms.domain.Branch;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Branch}.
 */
public interface BranchService {
    /**
     * Save a branch.
     *
     * @param branch the entity to save.
     * @return the persisted entity.
     */
    Branch save(Branch branch);

    /**
     * Partially updates a branch.
     *
     * @param branch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Branch> partialUpdate(Branch branch);

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Branch> findAll(Pageable pageable);

    /**
     * Get the "id" branch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Branch> findOne(Long id);

    /**
     * Delete the "id" branch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
