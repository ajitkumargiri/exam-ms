package com.ajit.examms.service.impl;

import com.ajit.examms.domain.College;
import com.ajit.examms.repository.CollegeRepository;
import com.ajit.examms.service.CollegeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link College}.
 */
@Service
@Transactional
public class CollegeServiceImpl implements CollegeService {

    private final Logger log = LoggerFactory.getLogger(CollegeServiceImpl.class);

    private final CollegeRepository collegeRepository;

    public CollegeServiceImpl(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Override
    public College save(College college) {
        log.debug("Request to save College : {}", college);
        return collegeRepository.save(college);
    }

    @Override
    public Optional<College> partialUpdate(College college) {
        log.debug("Request to partially update College : {}", college);

        return collegeRepository
            .findById(college.getId())
            .map(
                existingCollege -> {
                    if (college.getName() != null) {
                        existingCollege.setName(college.getName());
                    }
                    if (college.getCode() != null) {
                        existingCollege.setCode(college.getCode());
                    }

                    return existingCollege;
                }
            )
            .map(collegeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<College> findAll(Pageable pageable) {
        log.debug("Request to get all Colleges");
        return collegeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<College> findOne(Long id) {
        log.debug("Request to get College : {}", id);
        return collegeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete College : {}", id);
        collegeRepository.deleteById(id);
    }
}
