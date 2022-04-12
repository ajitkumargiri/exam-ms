package com.ajit.examms.service.impl;

import com.ajit.examms.domain.AcademicBatch;
import com.ajit.examms.repository.AcademicBatchRepository;
import com.ajit.examms.service.AcademicBatchService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AcademicBatch}.
 */
@Service
@Transactional
public class AcademicBatchServiceImpl implements AcademicBatchService {

    private final Logger log = LoggerFactory.getLogger(AcademicBatchServiceImpl.class);

    private final AcademicBatchRepository academicBatchRepository;

    public AcademicBatchServiceImpl(AcademicBatchRepository academicBatchRepository) {
        this.academicBatchRepository = academicBatchRepository;
    }

    @Override
    public AcademicBatch save(AcademicBatch academicBatch) {
        log.debug("Request to save AcademicBatch : {}", academicBatch);
        return academicBatchRepository.save(academicBatch);
    }

    @Override
    public Optional<AcademicBatch> partialUpdate(AcademicBatch academicBatch) {
        log.debug("Request to partially update AcademicBatch : {}", academicBatch);

        return academicBatchRepository
            .findById(academicBatch.getId())
            .map(
                existingAcademicBatch -> {
                    if (academicBatch.getName() != null) {
                        existingAcademicBatch.setName(academicBatch.getName());
                    }
                    if (academicBatch.getCode() != null) {
                        existingAcademicBatch.setCode(academicBatch.getCode());
                    }
                    if (academicBatch.getAcademicStartYear() != null) {
                        existingAcademicBatch.setAcademicStartYear(academicBatch.getAcademicStartYear());
                    }
                    if (academicBatch.getAcademicEndYear() != null) {
                        existingAcademicBatch.setAcademicEndYear(academicBatch.getAcademicEndYear());
                    }

                    return existingAcademicBatch;
                }
            )
            .map(academicBatchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcademicBatch> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicBatches");
        return academicBatchRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicBatch> findOne(Long id) {
        log.debug("Request to get AcademicBatch : {}", id);
        return academicBatchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicBatch : {}", id);
        academicBatchRepository.deleteById(id);
    }
}
