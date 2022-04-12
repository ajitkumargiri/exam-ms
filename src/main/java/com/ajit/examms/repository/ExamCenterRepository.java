package com.ajit.examms.repository;

import com.ajit.examms.domain.ExamCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExamCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamCenterRepository extends JpaRepository<ExamCenter, Long> {}
