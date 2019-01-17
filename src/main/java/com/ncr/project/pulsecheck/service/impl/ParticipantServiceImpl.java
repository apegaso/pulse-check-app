package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.MailService;
import com.ncr.project.pulsecheck.service.ParticipantService;
import com.google.common.collect.Sets;
import com.ncr.project.pulsecheck.domain.Event;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.EventRepository;
import com.ncr.project.pulsecheck.repository.ParticipantRepository;
import com.ncr.project.pulsecheck.repository.UserExtRepository;
import com.ncr.project.pulsecheck.service.dto.ParticipantDTO;
import com.ncr.project.pulsecheck.service.mapper.ParticipantMapper;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * Service Implementation for managing Participant.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;
    private final UserExtRepository userExtRepository;
    private final EventRepository eventRepository;
    private final MailService mailService;

    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper, UserExtRepository userExtRepository, EventRepository eventRepository, MailService mailService) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.userExtRepository=userExtRepository;
        this.eventRepository = eventRepository;
        this.mailService = mailService;
    }

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParticipantDTO save(ParticipantDTO participantDTO) {
        log.debug("Request to save Participant : {}", participantDTO);
        Participant participant = participantMapper.toEntity(participantDTO);
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return participantRepository.findAll(pageable)
            .map(participantMapper::toDto);
    }

    /**
     * Get all the Participant with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ParticipantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return participantRepository.findAllWithEagerRelationships(pageable).map(participantMapper::toDto);
    }
    

    /**
     * Get one participant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantDTO> findOne(Long id) {
        log.debug("Request to get Participant : {}", id);
        return participantRepository.findOneWithEagerRelationships(id)
            .map(participantMapper::toDto);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participant : {}", id);
        participantRepository.deleteById(id);
    }
    @Override
    public Participant createIfNotExists(UserExt userExt) {
        Participant ret = userExt.getParticipant();
        if(ret != null) return ret;
        ret = new Participant();
        ret.setUserExt(userExt);
        ret = participantRepository.save(ret);
        ret = participantRepository.findOneWithEagerRelationships(ret.getId()).get();
        return ret;
    }
    @Override
    public ParticipantDTO addParticipant(Long eventId, Long userExtId) {
        Optional<UserExt> userExt = userExtRepository.findById(userExtId);
        if(!userExt.isPresent()){
            throw new BadRequestAlertException("UserExt not exists", "Participant", "id not exist");
        }
        Participant ret = createIfNotExists(userExt.get());

        Optional<Event> event = eventRepository.findById(eventId);
        if(!event.isPresent()){
            throw new BadRequestAlertException("Event not exists", "Participant", "id not exist");
        }
        ret.addEvents(event.get());
        mailService.sendEventJoinEmail(ret.getUserExt().getUser(), event.get());

        ret = participantRepository.save(ret);
        return participantMapper.toDto(ret);
    }

    @Override
    public void delParticipant(Long eventId, Long userExtId) {
        Optional<UserExt> userExt = userExtRepository.findById(userExtId);
        if(!userExt.isPresent()){
            throw new BadRequestAlertException("UserExt not exists", "Participant", "id not exist");
        }
        Participant ret = createIfNotExists(userExt.get());

        Optional<Event> event = eventRepository.findById(eventId);
        if(!event.isPresent()){
            throw new BadRequestAlertException("Event not exists", "Participant", "id not exist");
        }
        ret.removeEvents(event.get());
        

        ret = participantRepository.save(ret);
        
    }


    @Override
    public Set<ParticipantDTO> findAllByEventId(Long id) {
        List<Participant> findAllByEventId = participantRepository.findAllByEventId(id);
        List<ParticipantDTO> dto = participantMapper.toDto(findAllByEventId);
        Set<ParticipantDTO> ret = Sets.newHashSet(dto.iterator());
        return ret;
	}
}
