package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.QuestionnaireService;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.Questionnaire;
import com.ncr.project.pulsecheck.repository.ParticipantRepository;
import com.ncr.project.pulsecheck.repository.QuestionnaireRepository;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionnaireMapper;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import afu.org.checkerframework.checker.oigj.qual.O;

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
    private final ParticipantRepository participantRepository;

    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository, QuestionnaireMapper questionnaireMapper, ParticipantRepository participantRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
        this.participantRepository = participantRepository;
    }

    /**
     * Save a questionnaire.
     *
     * @param questionnaireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionnaireDTO save(QuestionnaireDTO questionnaireDTO, Boolean checkNew) {
        log.debug("Request to save Questionnaire : {}", questionnaireDTO);
        Questionnaire questionnaire = questionnaireMapper.toEntity(questionnaireDTO);
        if(checkNew){
            Optional<Questionnaire> findByParticipantIdAndEventId = questionnaireRepository
                    .findByParticipantIdAndEventId(questionnaireDTO.getParticipantId(), questionnaireDTO.getEventId());
            if(findByParticipantIdAndEventId.isPresent()) throw new BadRequestAlertException("A new questionnaire cannot already have same participant and event id", "questionnaire", "idexists");
        }
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
        Optional<Participant> participant = participantRepository.findByUserExtId(userid);
        if(!participant.isPresent()) return Optional.empty();
        return questionnaireRepository.findByParticipantIdAndEventId(participant.get().getId(),eventid).map(questionnaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<QuestionnaireDTO>> findAllByUserExt(Long id) {
        Optional<Participant> participant = participantRepository.findByUserExtId(id);
        if(!participant.isPresent()) return Optional.empty();

        List<Questionnaire> findAllByParticipantId = questionnaireRepository
                .findAllByParticipantId(participant.get().getId());
        

        return Optional.ofNullable(questionnaireMapper.toDto(findAllByParticipantId));
        
    }
}
