package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing QuestionnaireAnswer.
 */
public interface QuestionnaireAnswerService {

    /**
     * Save a questionnaireAnswer.
     *
     * @param questionnaireAnswerDTO the entity to save
     * @return the persisted entity
     */
    QuestionnaireAnswerDTO save(QuestionnaireAnswerDTO questionnaireAnswerDTO);

    /**
     * Get all the questionnaireAnswers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionnaireAnswerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" questionnaireAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionnaireAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" questionnaireAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
