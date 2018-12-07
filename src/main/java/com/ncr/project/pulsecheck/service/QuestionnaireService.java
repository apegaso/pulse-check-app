package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Questionnaire.
 */
public interface QuestionnaireService {

    /**
     * Save a questionnaire.
     *
     * @param questionnaireDTO the entity to save
     * @return the persisted entity
     */
    QuestionnaireDTO save(QuestionnaireDTO questionnaireDTO, Boolean checkNew);

    /**
     * Get all the questionnaires.
     *
     * @return the list of entities
     */
    List<QuestionnaireDTO> findAll();


    /**
     * Get the "id" questionnaire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionnaireDTO> findOne(Long id);


    /**
     * Get the questionnaire by participant and event.
     *
     * @param userid the id of the UserExt
     * @param eventid the id of the Event
     * @return the entity
     */
    Optional<QuestionnaireDTO> findOneByUserExtAndEvent(Long userid, Long eventid);

    /**
     * Delete the "id" questionnaire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Optional<List<QuestionnaireDTO>> findAllByUserExt(Long id);
}
