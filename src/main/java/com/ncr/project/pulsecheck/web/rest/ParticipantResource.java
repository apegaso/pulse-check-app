package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Sets;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.EventService;
import com.ncr.project.pulsecheck.service.ParticipantService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.EventDTO;
import com.ncr.project.pulsecheck.service.dto.ParticipantDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Participant.
 */
@RestController
@RequestMapping("/api")
public class ParticipantResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantResource.class);

    private static final String ENTITY_NAME = "participant";

    private final ParticipantService participantService;
    private final UserService userService;
    private final UserExtService userExtService;
    private final EventService eventService;

    public ParticipantResource(ParticipantService participantService, UserService userService, UserExtService userExtService, EventService eventService) {
        this.participantService = participantService;
        this.userService =  userService;
        this.userExtService = userExtService;
        this.eventService = eventService;
    }

    /**
     * POST  /participants : Create a new participant.
     *
     * @param participantDTO the participantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new participantDTO, or with status 400 (Bad Request) if the participant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/participants")
    @Timed
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO) throws URISyntaxException {
        log.debug("REST request to save Participant : {}", participantDTO);
        if (participantDTO.getId() != null) {
            throw new BadRequestAlertException("A new participant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipantDTO result = participantService.save(participantDTO);
        return ResponseEntity.created(new URI("/api/participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /participants : Updates an existing participant.
     *
     * @param participantDTO the participantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated participantDTO,
     * or with status 400 (Bad Request) if the participantDTO is not valid,
     * or with status 500 (Internal Server Error) if the participantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/participants")
    @Timed
    public ResponseEntity<ParticipantDTO> updateParticipant(@RequestBody ParticipantDTO participantDTO) throws URISyntaxException {
        log.debug("REST request to update Participant : {}", participantDTO);
        if (participantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParticipantDTO result = participantService.save(participantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, participantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /participants : get all the participants.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of participants in body
     */
    @GetMapping("/participants")
    @Timed
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Participants");
        Page<ParticipantDTO> page;
        if (eagerload) {
            page = participantService.findAllWithEagerRelationships(pageable);
        } else {
            page = participantService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/participants?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /participants/:id : get the "id" participant.
     *
     * @param id the id of the participantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the participantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/participants/{id}")
    @Timed
    public ResponseEntity<ParticipantDTO> getParticipant(@PathVariable Long id) {
        log.debug("REST request to get Participant : {}", id);
        Optional<ParticipantDTO> participantDTO = participantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantDTO);
    }

    /**
     * DELETE  /participants/:id : delete the "id" participant.
     *
     * @param id the id of the participantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/participants/{id}")
    @Timed
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        log.debug("REST request to delete Participant : {}", id);
        participantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * PUT  /participants/add/:eventId/:userExtId : add an the "userExtId" user as an lead for the "eventId" event 
     *
     * @param eventId the eventId to update
     * @param userExtId the userExtId to add
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgAdminDTO,
     * or with status 400 (Bad Request) if the one of the orgId or userExtId is not valid,
     * or with status 500 (Internal Server Error) if the orgAdmin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping(path="/participants/add/{eventId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<ParticipantDTO> addParticipant(@PathVariable Long eventId, @RequestParam(required = false, value="userExtId") Long userExtId, @RequestParam(required = false, value="email") String email) throws URISyntaxException {

        
        log.debug("REST request to add participant userExt {} to Event : {}", userExtId, eventId);
        if (eventId == null) {
            throw new BadRequestAlertException("Invalid eventId", ENTITY_NAME, "idnull");
        }
        if (userExtId == null && (email == null || email.isEmpty())) {
            throw new BadRequestAlertException("Missing user. one of email or userExtId parameter must filled", ENTITY_NAME, "idnull");
        }

        
        if(email != null && !email.isEmpty()) {
            Optional<EventDTO> event = eventService.findOne(eventId);
            if(!event.isPresent()) throw new BadRequestAlertException("Event not found", ENTITY_NAME, "idnull");
            Long organizationId = event.get().getOrganizationId();
            userService.createUserFromEmail(organizationId, email,
                    Sets.newHashSet(AuthoritiesConstants.PARTICIPANT, AuthoritiesConstants.USER));
            //create userExt if not exists
            UserExt createIfNotExists = userExtService.createIfNotExists(null, email);
            if(createIfNotExists == null) throw new BadRequestAlertException("Error creating new UserExt", ENTITY_NAME, "userext null");
            userExtId = createIfNotExists.getId();
        }

        ParticipantDTO participantDTO = participantService.addParticipant(eventId, userExtId);
        
        return ResponseUtil.wrapOrNotFound(Optional.of(participantDTO));
    }
    @DeleteMapping("/participants/del/{eventId}/{userExtId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<Void> delParticipant(@PathVariable Long eventId, @PathVariable Long userExtId) throws URISyntaxException {
        log.debug("REST request to del participant userExt {} to Event : {}", userExtId, eventId);
        if (eventId == null) {
            throw new BadRequestAlertException("Invalid eventId", ENTITY_NAME, "idnull");
        }
        if (userExtId == null) {
            throw new BadRequestAlertException("Invalid userExtId", ENTITY_NAME, "idnull");
        }

        participantService.delParticipant(eventId, userExtId);
        
        return ResponseEntity.ok().build();
    }
}
