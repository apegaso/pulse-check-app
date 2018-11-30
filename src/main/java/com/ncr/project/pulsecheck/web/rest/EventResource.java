package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Sets;
import com.ncr.project.pulsecheck.domain.User;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.service.EventService;
import com.ncr.project.pulsecheck.service.ParticipantService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.dto.EventDTO;
import com.ncr.project.pulsecheck.service.dto.EventExtendedDTO;
import com.ncr.project.pulsecheck.service.dto.ParticipantDTO;
import com.ncr.project.pulsecheck.service.dto.UserDTO;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;
import com.ncr.project.pulsecheck.service.dto.UserExtWRelationsDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    private final EventService eventService;
    private final UserExtService userExtService;
    private final UserService userService;
    private final ClientLeadService clientLeadService;
    private final ParticipantService participantService;

    public EventResource(EventService eventService, UserExtService userExtService, ClientLeadService clientLeadService, ParticipantService participantService, UserService userService) {
        this.eventService = eventService;
        this.userExtService = userExtService;
        this.clientLeadService = clientLeadService;
        this.participantService = participantService;
        this.userService = userService;
    }

    /**
     * POST  /events : Create a new event.
     *
     * @param eventDTO the eventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventDTO, or with status 400 (Bad Request) if the event has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/events")
    @Timed
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to save Event : {}", eventDTO);
        if (eventDTO.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventDTO result = eventService.save(eventDTO);
        return ResponseEntity.created(new URI("/api/events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/events/extended")
    @Timed
    public ResponseEntity<EventExtendedDTO> createEventExtended(@Valid @RequestBody EventExtendedDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to save EventExtended : {}", eventDTO);
        if (eventDTO.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        EventDTO eventDtoNew = eventService.save(eventDTO);

        EventExtendedDTO result = new EventExtendedDTO(eventDtoNew);
        
        for(UserExtDTO u : eventDTO.getParticipants()) {
            if(u.getEmail() == null || u.getEmail().isEmpty()) throw new BadRequestAlertException("A new user must have the email", ENTITY_NAME, "email null");
            
            //search for user
            Optional<User> userWithAuthoritiesByEmail = userService.getUserWithAuthoritiesByEmail(u.getEmail());
            //createUser if not exists
            if(!userWithAuthoritiesByEmail.isPresent()){
                UserDTO userDTO = new UserDTO();
                userDTO.setLogin(u.getEmail());
                userDTO.setEmail(u.getEmail());
                userDTO.setOrganizationId(eventDtoNew.getOrganizationId());
                userDTO.setAuthorities(Sets.newHashSet(AuthoritiesConstants.USER, AuthoritiesConstants.PARTICIPANT));

                User createdUser = userService.createUser(userDTO);
                log.debug("User created: {}",createdUser);
                u.setUserId(createdUser.getId());
            }
            
            //create userExt if not exists
            UserExt createIfNotExists = userExtService.createIfNotExists(null, u.getEmail());
            if(createIfNotExists == null) throw new BadRequestAlertException("Error creating new UserExt", ENTITY_NAME, "userext null");
            u.setId(createIfNotExists.getId());

            //assign as participants
            participantService.addParticipant(result.getId(), u.getId());
        }
        for(UserExtDTO u : eventDTO.getClientLeads()){
            if(u.getEmail() == null || u.getEmail().isEmpty()) throw new BadRequestAlertException("A new user must have the email", ENTITY_NAME, "email null");
            
            //search for user
            Optional<User> userWithAuthoritiesByEmail = userService.getUserWithAuthoritiesByEmail(u.getEmail());
            //createUser if not exists
            if(!userWithAuthoritiesByEmail.isPresent()){
                UserDTO userDTO = new UserDTO();
                userDTO.setLogin(u.getEmail());
                userDTO.setEmail(u.getEmail());
                userDTO.setAuthorities(Sets.newHashSet(AuthoritiesConstants.USER, AuthoritiesConstants.CLIENT_LEAD));
                userDTO.setOrganizationId(eventDtoNew.getOrganizationId());
                User createdUser = userService.createUser(userDTO);
                log.debug("User created: {}",createdUser);
                u.setUserId(createdUser.getId());
            }
            
            //create userExt if not exists
            UserExt createIfNotExists = userExtService.createIfNotExists(null, u.getEmail());
            if(createIfNotExists == null) throw new BadRequestAlertException("Error creating new UserExt", ENTITY_NAME, "userext null");
            u.setId(createIfNotExists.getId());

            //assign as client lead
            clientLeadService.addClientLead(result.getId(), u.getId());
        }
        

        return ResponseEntity.created(new URI("/api/events/extended/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/events/extended/{id}")
    @Timed
    public ResponseEntity<EventExtendedDTO> getEventExtended(@PathVariable Long id) throws URISyntaxException {
        Optional<EventDTO> eventOptional = eventService.findOne(id);
        if(!eventOptional.isPresent()){
            return ResponseUtil.wrapOrNotFound(Optional.empty());
        }

        EventExtendedDTO result = new EventExtendedDTO(eventOptional.get());
        
        Set<ParticipantDTO> participants = participantService.findAllByEventId(id);

        Set<UserExtDTO> participantsUserExt = Sets.newHashSet();
        participants.forEach(p -> {
            userExtService.findOne(p.getUserExtId()).ifPresent(participantsUserExt::add);
        });
        result.setParticipants(participantsUserExt);

        Set<UserExtDTO> clientLeadsUserExt = Sets.newHashSet();
        Set<ClientLeadDTO> clientLeads = clientLeadService.fineAllByEventId(id);
        clientLeads.forEach(p -> {
            userExtService.findOne(p.getUserExtId()).ifPresent(clientLeadsUserExt::add);
        });
        result.setClientLeads(clientLeadsUserExt);

        return ResponseUtil.wrapOrNotFound(Optional.of(result));
    }


    


    /**
     * PUT  /events : Updates an existing event.
     *
     * @param eventDTO the eventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventDTO,
     * or with status 400 (Bad Request) if the eventDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/events")
    @Timed
    public ResponseEntity<EventDTO> updateEvent(@Valid @RequestBody EventDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to update Event : {}", eventDTO);
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventDTO result = eventService.save(eventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /events : get all the events.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of events in body
     */
    @GetMapping("/events")
    @Timed
    public ResponseEntity<List<EventDTO>> getAllEvents(Pageable pageable) {
        log.debug("REST request to get a page of Events");
        Page<EventDTO> page = eventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /events/:id : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/events/{id}")
    @Timed
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        log.debug("REST request to get Event : {}", id);
        Optional<EventDTO> eventDTO = eventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventDTO);
    }

    /**
     * DELETE  /events/:id : delete the "id" event.
     *
     * @param id the id of the eventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/events/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
