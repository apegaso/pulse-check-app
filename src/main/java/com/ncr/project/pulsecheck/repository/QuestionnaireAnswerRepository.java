package com.ncr.project.pulsecheck.repository;

import java.util.List;

import com.ncr.project.pulsecheck.domain.QuestionnaireAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionnaireAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {

    @Query("select questionnaireanswer from QuestionnaireAnswer questionnaireanswer left join fetch questionnaireanswer.questionnaire questionnaire where questionnaire.id =:questionnaireid")
	List<QuestionnaireAnswer> findAllByQuestionnaireId(@Param("questionnaireid") Long questionnaireid);

}
