package com.ajit.examms.service.impl;

import com.ajit.examms.domain.SubjectPaper;
import com.ajit.examms.repository.SubjectPaperRepository;
import com.ajit.examms.service.SubjectPaperService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubjectPaper}.
 */
@Service
@Transactional
public class SubjectPaperServiceImpl implements SubjectPaperService {

    private final Logger log = LoggerFactory.getLogger(SubjectPaperServiceImpl.class);

    private final SubjectPaperRepository subjectPaperRepository;

    public SubjectPaperServiceImpl(SubjectPaperRepository subjectPaperRepository) {
        this.subjectPaperRepository = subjectPaperRepository;
    }

    @Override
    public SubjectPaper save(SubjectPaper subjectPaper) {
        log.debug("Request to save SubjectPaper : {}", subjectPaper);
        return subjectPaperRepository.save(subjectPaper);
    }

    @Override
    public Optional<SubjectPaper> partialUpdate(SubjectPaper subjectPaper) {
        log.debug("Request to partially update SubjectPaper : {}", subjectPaper);

        return subjectPaperRepository
            .findById(subjectPaper.getId())
            .map(
                existingSubjectPaper -> {
                    if (subjectPaper.getName() != null) {
                        existingSubjectPaper.setName(subjectPaper.getName());
                    }
                    if (subjectPaper.getCode() != null) {
                        existingSubjectPaper.setCode(subjectPaper.getCode());
                    }
                    if (subjectPaper.getType() != null) {
                        existingSubjectPaper.setType(subjectPaper.getType());
                    }
                    if (subjectPaper.getFullMark() != null) {
                        existingSubjectPaper.setFullMark(subjectPaper.getFullMark());
                    }
                    if (subjectPaper.getPassMark() != null) {
                        existingSubjectPaper.setPassMark(subjectPaper.getPassMark());
                    }

                    return existingSubjectPaper;
                }
            )
            .map(subjectPaperRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubjectPaper> findAll(Pageable pageable) {
        log.debug("Request to get all SubjectPapers");
        return subjectPaperRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectPaper> findOne(Long id) {
        log.debug("Request to get SubjectPaper : {}", id);
        return subjectPaperRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectPaper : {}", id);
        subjectPaperRepository.deleteById(id);
    }
}
