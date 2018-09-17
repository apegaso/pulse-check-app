package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.QuestionGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {

}
