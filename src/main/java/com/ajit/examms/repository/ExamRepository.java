package com.ajit.examms.repository;

import com.ajit.examms.domain.Exam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Exam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {}
