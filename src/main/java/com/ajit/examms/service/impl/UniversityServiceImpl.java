package com.ajit.examms.service.impl;

import com.ajit.examms.domain.University;
import com.ajit.examms.repository.UniversityRepository;
import com.ajit.examms.service.UniversityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link University}.
 */
@Service
@Transactional
public class UniversityServiceImpl implements UniversityService {

    private final Logger log = LoggerFactory.getLogger(UniversityServiceImpl.class);

    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public University save(University university) {
        log.debug("Request to save University : {}", university);
        return universityRepository.save(university);
    }

    @Override
    public Optional<University> partialUpdate(University university) {
        log.debug("Request to partially update University : {}", university);

        return universityRepository
            .findById(university.getId())
            .map(
                existingUniversity -> {
                    if (university.getName() != null) {
                        existingUniversity.setName(university.getName());
                    }
                    if (university.getCode() != null) {
                        existingUniversity.setCode(university.getCode());
                    }

                    return existingUniversity;
                }
            )
            .map(universityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<University> findAll() {
        log.debug("Request to get all Universities");
        return universityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<University> findOne(Long id) {
        log.debug("Request to get University : {}", id);
        return universityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete University : {}", id);
        universityRepository.deleteById(id);
    }
}
