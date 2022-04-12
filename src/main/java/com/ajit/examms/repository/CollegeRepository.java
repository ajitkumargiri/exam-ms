package com.ajit.examms.repository;

import com.ajit.examms.domain.College;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the College entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {}
