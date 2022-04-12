package com.ajit.examms.repository;

import com.ajit.examms.domain.University;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the University entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {}
