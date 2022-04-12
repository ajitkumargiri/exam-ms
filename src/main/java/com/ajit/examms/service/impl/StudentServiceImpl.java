package com.ajit.examms.service.impl;

import com.ajit.examms.domain.Student;
import com.ajit.examms.repository.StudentRepository;
import com.ajit.examms.service.StudentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(
                existingStudent -> {
                    if (student.getRegNumber() != null) {
                        existingStudent.setRegNumber(student.getRegNumber());
                    }
                    if (student.getFirstName() != null) {
                        existingStudent.setFirstName(student.getFirstName());
                    }
                    if (student.getMiddleName() != null) {
                        existingStudent.setMiddleName(student.getMiddleName());
                    }
                    if (student.getLastName() != null) {
                        existingStudent.setLastName(student.getLastName());
                    }
                    if (student.getDob() != null) {
                        existingStudent.setDob(student.getDob());
                    }
                    if (student.getFatherName() != null) {
                        existingStudent.setFatherName(student.getFatherName());
                    }
                    if (student.getMotherName() != null) {
                        existingStudent.setMotherName(student.getMotherName());
                    }
                    if (student.getEmail() != null) {
                        existingStudent.setEmail(student.getEmail());
                    }
                    if (student.getMobileNumber() != null) {
                        existingStudent.setMobileNumber(student.getMobileNumber());
                    }
                    if (student.getNationality() != null) {
                        existingStudent.setNationality(student.getNationality());
                    }
                    if (student.getGender() != null) {
                        existingStudent.setGender(student.getGender());
                    }
                    if (student.getReligion() != null) {
                        existingStudent.setReligion(student.getReligion());
                    }
                    if (student.getCatagory() != null) {
                        existingStudent.setCatagory(student.getCatagory());
                    }
                    if (student.getMaritialStatus() != null) {
                        existingStudent.setMaritialStatus(student.getMaritialStatus());
                    }
                    if (student.getAdharNumber() != null) {
                        existingStudent.setAdharNumber(student.getAdharNumber());
                    }
                    if (student.getBloodGroup() != null) {
                        existingStudent.setBloodGroup(student.getBloodGroup());
                    }
                    if (student.getFatherMobileNumber() != null) {
                        existingStudent.setFatherMobileNumber(student.getFatherMobileNumber());
                    }
                    if (student.getFatherEmailId() != null) {
                        existingStudent.setFatherEmailId(student.getFatherEmailId());
                    }
                    if (student.getImage() != null) {
                        existingStudent.setImage(student.getImage());
                    }
                    if (student.getImageContentType() != null) {
                        existingStudent.setImageContentType(student.getImageContentType());
                    }
                    if (student.getSignature() != null) {
                        existingStudent.setSignature(student.getSignature());
                    }
                    if (student.getSignatureContentType() != null) {
                        existingStudent.setSignatureContentType(student.getSignatureContentType());
                    }
                    if (student.getAdhar() != null) {
                        existingStudent.setAdhar(student.getAdhar());
                    }
                    if (student.getAdharContentType() != null) {
                        existingStudent.setAdharContentType(student.getAdharContentType());
                    }

                    return existingStudent;
                }
            )
            .map(studentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
