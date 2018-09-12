package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.QuestionnaireService;
import com.ncr.project.pulsecheck.domain.Questionnaire;
import com.ncr.project.pulsecheck.repository.QuestionnaireRepository;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionnaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Questionnaire.
 */
@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository, QuestionnaireMapper questionnaireMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
    }

    /**
     * Save a questionnaire.
     *
     * @param questionnaireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionnaireDTO save(QuestionnaireDTO questionnaireDTO) {
        log.debug("Request to save Questionnaire : {}", questionnaireDTO);
        Questionnaire questionnaire = questionnaireMapper.toEntity(questionnaireDTO);
        questionnaire = questionnaireRepository.save(questionnaire);
        return questionnaireMapper.toDto(questionnaire);
    }

    /**
     * Get all the questionnaires.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionnaireDTO> findAll() {
        log.debug("Request to get all Questionnaires");
        return questionnaireRepository.findAll().stream()
            .map(questionnaireMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one questionnaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireDTO> findOne(Long id) {
        log.debug("Request to get Questionnaire : {}", id);
        return questionnaireRepository.findById(id)
            .map(questionnaireMapper::toDto);
    }

    /**
     * Delete the questionnaire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Questionnaire : {}", id);
        questionnaireRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireDTO> findOneByUserExtAndEvent(Long userid, Long eventid) {
        return questionnaireRepository.findByUserExtIdAndEventId(userid,eventid).map(questionnaireMapper::toDto);
    }
}
