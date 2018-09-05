package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.QuestionnaireAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionnaireAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {

}
