package com.ajit.examms.service.impl;

import com.ajit.examms.domain.Exam;
import com.ajit.examms.repository.ExamRepository;
import com.ajit.examms.service.ExamService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Exam}.
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam save(Exam exam) {
        log.debug("Request to save Exam : {}", exam);
        return examRepository.save(exam);
    }

    @Override
    public Optional<Exam> partialUpdate(Exam exam) {
        log.debug("Request to partially update Exam : {}", exam);

        return examRepository
            .findById(exam.getId())
            .map(
                existingExam -> {
                    if (exam.getName() != null) {
                        existingExam.setName(exam.getName());
                    }
                    if (exam.getType() != null) {
                        existingExam.setType(exam.getType());
                    }
                    if (exam.getCode() != null) {
                        existingExam.setCode(exam.getCode());
                    }
                    if (exam.getDate() != null) {
                        existingExam.setDate(exam.getDate());
                    }
                    if (exam.getStartTime() != null) {
                        existingExam.setStartTime(exam.getStartTime());
                    }
                    if (exam.getEndTime() != null) {
                        existingExam.setEndTime(exam.getEndTime());
                    }

                    return existingExam;
                }
            )
            .map(examRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exam> findAll(Pageable pageable) {
        log.debug("Request to get all Exams");
        return examRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Exam> findOne(Long id) {
        log.debug("Request to get Exam : {}", id);
        return examRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exam : {}", id);
        examRepository.deleteById(id);
    }
}
