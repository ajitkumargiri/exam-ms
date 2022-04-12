package com.ajit.examms.service.impl;

import com.ajit.examms.domain.ExamApplicationForm;
import com.ajit.examms.repository.ExamApplicationFormRepository;
import com.ajit.examms.service.ExamApplicationFormService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExamApplicationForm}.
 */
@Service
@Transactional
public class ExamApplicationFormServiceImpl implements ExamApplicationFormService {

    private final Logger log = LoggerFactory.getLogger(ExamApplicationFormServiceImpl.class);

    private final ExamApplicationFormRepository examApplicationFormRepository;

    public ExamApplicationFormServiceImpl(ExamApplicationFormRepository examApplicationFormRepository) {
        this.examApplicationFormRepository = examApplicationFormRepository;
    }

    @Override
    public ExamApplicationForm save(ExamApplicationForm examApplicationForm) {
        log.debug("Request to save ExamApplicationForm : {}", examApplicationForm);
        return examApplicationFormRepository.save(examApplicationForm);
    }

    @Override
    public Optional<ExamApplicationForm> partialUpdate(ExamApplicationForm examApplicationForm) {
        log.debug("Request to partially update ExamApplicationForm : {}", examApplicationForm);

        return examApplicationFormRepository
            .findById(examApplicationForm.getId())
            .map(
                existingExamApplicationForm -> {
                    if (examApplicationForm.getRegNumber() != null) {
                        existingExamApplicationForm.setRegNumber(examApplicationForm.getRegNumber());
                    }
                    if (examApplicationForm.getFirstName() != null) {
                        existingExamApplicationForm.setFirstName(examApplicationForm.getFirstName());
                    }
                    if (examApplicationForm.getMiddleName() != null) {
                        existingExamApplicationForm.setMiddleName(examApplicationForm.getMiddleName());
                    }
                    if (examApplicationForm.getLastName() != null) {
                        existingExamApplicationForm.setLastName(examApplicationForm.getLastName());
                    }
                    if (examApplicationForm.getDob() != null) {
                        existingExamApplicationForm.setDob(examApplicationForm.getDob());
                    }
                    if (examApplicationForm.getFatherName() != null) {
                        existingExamApplicationForm.setFatherName(examApplicationForm.getFatherName());
                    }
                    if (examApplicationForm.getEmail() != null) {
                        existingExamApplicationForm.setEmail(examApplicationForm.getEmail());
                    }
                    if (examApplicationForm.getMobileNumber() != null) {
                        existingExamApplicationForm.setMobileNumber(examApplicationForm.getMobileNumber());
                    }
                    if (examApplicationForm.getNationality() != null) {
                        existingExamApplicationForm.setNationality(examApplicationForm.getNationality());
                    }
                    if (examApplicationForm.getGender() != null) {
                        existingExamApplicationForm.setGender(examApplicationForm.getGender());
                    }
                    if (examApplicationForm.getReligion() != null) {
                        existingExamApplicationForm.setReligion(examApplicationForm.getReligion());
                    }
                    if (examApplicationForm.getAdharNumber() != null) {
                        existingExamApplicationForm.setAdharNumber(examApplicationForm.getAdharNumber());
                    }
                    if (examApplicationForm.getBloodGroup() != null) {
                        existingExamApplicationForm.setBloodGroup(examApplicationForm.getBloodGroup());
                    }
                    if (examApplicationForm.getIsApproved() != null) {
                        existingExamApplicationForm.setIsApproved(examApplicationForm.getIsApproved());
                    }
                    if (examApplicationForm.getCatagory() != null) {
                        existingExamApplicationForm.setCatagory(examApplicationForm.getCatagory());
                    }
                    if (examApplicationForm.getIdentityType() != null) {
                        existingExamApplicationForm.setIdentityType(examApplicationForm.getIdentityType());
                    }
                    if (examApplicationForm.getIdentityNumber() != null) {
                        existingExamApplicationForm.setIdentityNumber(examApplicationForm.getIdentityNumber());
                    }
                    if (examApplicationForm.getImage() != null) {
                        existingExamApplicationForm.setImage(examApplicationForm.getImage());
                    }
                    if (examApplicationForm.getImageContentType() != null) {
                        existingExamApplicationForm.setImageContentType(examApplicationForm.getImageContentType());
                    }
                    if (examApplicationForm.getSignature() != null) {
                        existingExamApplicationForm.setSignature(examApplicationForm.getSignature());
                    }
                    if (examApplicationForm.getSignatureContentType() != null) {
                        existingExamApplicationForm.setSignatureContentType(examApplicationForm.getSignatureContentType());
                    }
                    if (examApplicationForm.getAdhar() != null) {
                        existingExamApplicationForm.setAdhar(examApplicationForm.getAdhar());
                    }
                    if (examApplicationForm.getAdharContentType() != null) {
                        existingExamApplicationForm.setAdharContentType(examApplicationForm.getAdharContentType());
                    }
                    if (examApplicationForm.getImagePath() != null) {
                        existingExamApplicationForm.setImagePath(examApplicationForm.getImagePath());
                    }
                    if (examApplicationForm.getSignPath() != null) {
                        existingExamApplicationForm.setSignPath(examApplicationForm.getSignPath());
                    }
                    if (examApplicationForm.getAdharPath() != null) {
                        existingExamApplicationForm.setAdharPath(examApplicationForm.getAdharPath());
                    }

                    return existingExamApplicationForm;
                }
            )
            .map(examApplicationFormRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamApplicationForm> findAll(Pageable pageable) {
        log.debug("Request to get all ExamApplicationForms");
        return examApplicationFormRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamApplicationForm> findOne(Long id) {
        log.debug("Request to get ExamApplicationForm : {}", id);
        return examApplicationFormRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamApplicationForm : {}", id);
        examApplicationFormRepository.deleteById(id);
    }
}
