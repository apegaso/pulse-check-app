package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.QuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Question.
 */
public interface QuestionService {

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    QuestionDTO save(QuestionDTO questionDTO);

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionDTO> findAll(Pageable pageable);

    /**
     * Get all the Question with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<QuestionDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" question.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionDTO> findOne(Long id);

    /**
     * Get the "order id" question.
     *
     * @param orderid the order id of the entity
     * @return the entity
     */
    Optional<QuestionDTO> findOneByOrder(Integer orderid);
    /**
     * Count total number of questions
     *
     */
    public Long countAll() ;
    
    /**
     * Delete the "id" question.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
