package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.QuestionnaireAnswerService;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.QuestionnaireAnswer;
import com.ncr.project.pulsecheck.repository.QuestionnaireAnswerRepository;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionnaireAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing QuestionnaireAnswer.
 */
@Service
@Transactional
public class QuestionnaireAnswerServiceImpl implements QuestionnaireAnswerService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireAnswerServiceImpl.class);

    private final QuestionnaireAnswerRepository questionnaireAnswerRepository;

    private final QuestionnaireAnswerMapper questionnaireAnswerMapper;

    public QuestionnaireAnswerServiceImpl(QuestionnaireAnswerRepository questionnaireAnswerRepository, QuestionnaireAnswerMapper questionnaireAnswerMapper) {
        this.questionnaireAnswerRepository = questionnaireAnswerRepository;
        this.questionnaireAnswerMapper = questionnaireAnswerMapper;
    }

    /**
     * Save a questionnaireAnswer.
     *
     * @param questionnaireAnswerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionnaireAnswerDTO save(QuestionnaireAnswerDTO questionnaireAnswerDTO) {
        log.debug("Request to save QuestionnaireAnswer : {}", questionnaireAnswerDTO);
        QuestionnaireAnswer questionnaireAnswer = questionnaireAnswerMapper.toEntity(questionnaireAnswerDTO);
        questionnaireAnswer = questionnaireAnswerRepository.save(questionnaireAnswer);
        return questionnaireAnswerMapper.toDto(questionnaireAnswer);
    }

    /**
     * Get all the questionnaireAnswers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionnaireAnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionnaireAnswers");
        return questionnaireAnswerRepository.findAll(pageable)
            .map(questionnaireAnswerMapper::toDto);
    }


    /**
     * Get one questionnaireAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionnaireAnswerDTO> findOne(Long id) {
        log.debug("Request to get QuestionnaireAnswer : {}", id);
        return questionnaireAnswerRepository.findById(id)
            .map(questionnaireAnswerMapper::toDto);
    }

    /**
     * Delete the questionnaireAnswer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionnaireAnswer : {}", id);
        questionnaireAnswerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<QuestionnaireAnswerDTO>> findAllByQuestionnaire(Long questionnaireId) {
        List<QuestionnaireAnswer> findAllByParticipantId = questionnaireAnswerRepository
                .findAllByQuestionnaireId(questionnaireId);

        return Optional.ofNullable(questionnaireAnswerMapper.toDto(findAllByParticipantId));
    }
}
