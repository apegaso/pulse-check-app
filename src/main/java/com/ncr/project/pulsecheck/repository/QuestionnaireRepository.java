package com.ncr.project.pulsecheck.repository;

import java.util.List;
import java.util.Optional;


import com.ncr.project.pulsecheck.domain.Questionnaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Questionnaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query("select questionnaire from Questionnaire questionnaire left join fetch questionnaire.event event join fetch questionnaire.participant participant where participant.id =:userid and event.id =:eventid")
    Optional<Questionnaire> findByUserExtIdAndEventId(@Param("userid")Long userid, @Param("eventid")Long eventid);
    
    @Query("select questionnaire from Questionnaire questionnaire left join fetch questionnaire.event event join fetch questionnaire.participant participant where event.id =:eventid")
	List<Questionnaire> findAllByEventId(@Param("eventid") Long eventid);

}
