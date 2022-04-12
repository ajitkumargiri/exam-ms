package com.ajit.examms.service.impl;

import com.ajit.examms.domain.ExamCenter;
import com.ajit.examms.repository.ExamCenterRepository;
import com.ajit.examms.service.ExamCenterService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExamCenter}.
 */
@Service
@Transactional
public class ExamCenterServiceImpl implements ExamCenterService {

    private final Logger log = LoggerFactory.getLogger(ExamCenterServiceImpl.class);

    private final ExamCenterRepository examCenterRepository;

    public ExamCenterServiceImpl(ExamCenterRepository examCenterRepository) {
        this.examCenterRepository = examCenterRepository;
    }

    @Override
    public ExamCenter save(ExamCenter examCenter) {
        log.debug("Request to save ExamCenter : {}", examCenter);
        return examCenterRepository.save(examCenter);
    }

    @Override
    public Optional<ExamCenter> partialUpdate(ExamCenter examCenter) {
        log.debug("Request to partially update ExamCenter : {}", examCenter);

        return examCenterRepository
            .findById(examCenter.getId())
            .map(
                existingExamCenter -> {
                    if (examCenter.getName() != null) {
                        existingExamCenter.setName(examCenter.getName());
                    }
                    if (examCenter.getCode() != null) {
                        existingExamCenter.setCode(examCenter.getCode());
                    }

                    return existingExamCenter;
                }
            )
            .map(examCenterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamCenter> findAll() {
        log.debug("Request to get all ExamCenters");
        return examCenterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamCenter> findOne(Long id) {
        log.debug("Request to get ExamCenter : {}", id);
        return examCenterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamCenter : {}", id);
        examCenterRepository.deleteById(id);
    }
}
