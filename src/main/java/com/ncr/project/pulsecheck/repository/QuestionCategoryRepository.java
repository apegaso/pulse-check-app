package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.QuestionCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {

}
