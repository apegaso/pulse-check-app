package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.EventService;
import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.domain.Event;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.Questionnaire;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.repository.EventRepository;
import com.ncr.project.pulsecheck.repository.ParticipantRepository;
import com.ncr.project.pulsecheck.repository.QuestionnaireRepository;
import com.ncr.project.pulsecheck.service.dto.EventDTO;
import com.ncr.project.pulsecheck.service.mapper.EventMapper;
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
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final ClientLeadRepository clientLeadRepository;
    
    private final QuestionnaireRepository questionnaireRepository;

    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, ParticipantRepository participantRepository, QuestionnaireRepository questionnaireRepository, ClientLeadRepository clientLeadRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.participantRepository = participantRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.clientLeadRepository = clientLeadRepository;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDto);
    }


    /**
     * Get one event by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id)
            .map(eventMapper::toDto);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        List<Questionnaire> questionnaires = questionnaireRepository.findAllByEventId(id);
        if(questionnaires != null && questionnaires.size() > 0) 
            throw new BadRequestAlertException("Event not empty. Threre are questionaire associated. Please delete first questionnaires than delete the event", "EventService", "questionnaires not null");
         
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent())
            throw new BadRequestAlertException("Event not found", "EventService", "id not found");
        Event event = eventOptional.get();
        Set<Participant> participants = event.getParticipants();
        if(participants != null && participants.size() > 0){
            for(Participant p : participants){
                p.removeEvents(event);
                participantRepository.save(p);
            }
        }
        Set<ClientLead> leads = event.getLeads();
        if(leads != null && leads.size() > 0){
            for(ClientLead l : leads){
                l.removeEvents(event);
                clientLeadRepository.save(l);
            }
        }
        eventRepository.deleteById(id);
    }
}
