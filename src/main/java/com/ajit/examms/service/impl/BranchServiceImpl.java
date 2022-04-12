package com.ajit.examms.service.impl;

import com.ajit.examms.domain.Branch;
import com.ajit.examms.repository.BranchRepository;
import com.ajit.examms.service.BranchService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;

    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        return branchRepository.save(branch);
    }

    @Override
    public Optional<Branch> partialUpdate(Branch branch) {
        log.debug("Request to partially update Branch : {}", branch);

        return branchRepository
            .findById(branch.getId())
            .map(
                existingBranch -> {
                    if (branch.getName() != null) {
                        existingBranch.setName(branch.getName());
                    }
                    if (branch.getCode() != null) {
                        existingBranch.setCode(branch.getCode());
                    }

                    return existingBranch;
                }
            )
            .map(branchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Branch> findAll(Pageable pageable) {
        log.debug("Request to get all Branches");
        return branchRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Branch> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
