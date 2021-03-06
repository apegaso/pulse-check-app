package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.QuestionService;
import com.ncr.project.pulsecheck.domain.Question;
import com.ncr.project.pulsecheck.repository.QuestionRepository;
import com.ncr.project.pulsecheck.service.dto.QuestionDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Question.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionRepository.findAll(pageable)
            .map(questionMapper::toDto);
    }

    /**
     * Get all the Question with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<QuestionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return questionRepository.findAllWithEagerRelationships(pageable).map(questionMapper::toDto);
    }
    

    /**
     * Get one question by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findOneWithEagerRelationships(id)
            .map(questionMapper::toDto);
    }

    /**
     * Delete the question by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }

    /**
     * find one Question by order ID
     *
     * @param orderid the order id of the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOneByOrder(Integer orderid) {
        log.debug("Request to get Question by Order : {}", orderid);
        return questionRepository.findOneByOrderWithEagerRelationships(orderid)
            .map(questionMapper::toDto);
        
    }
    /**
     * Count total number of questions
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Long countAll() {
        log.debug("Count all Questions");
        return questionRepository.count();
            
        
    }
}
