package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Sets;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.service.EventService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;
import com.ncr.project.pulsecheck.service.dto.EventDTO;

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
 * REST controller for managing ClientLead.
 */
@RestController
@RequestMapping("/api")
public class ClientLeadResource {

    private final Logger log = LoggerFactory.getLogger(ClientLeadResource.class);

    private static final String ENTITY_NAME = "clientLead";

    private final ClientLeadService clientLeadService;
    private final UserService userService;
    private final UserExtService userExtService;
    private final EventService eventService;

    public ClientLeadResource(ClientLeadService clientLeadService, UserService userService, UserExtService userExtService, EventService eventService) {
        this.clientLeadService = clientLeadService;
        this.userService =  userService;
        this.userExtService = userExtService;
        this.eventService = eventService;
    }

    /**
     * POST  /client-leads : Create a new clientLead.
     *
     * @param clientLeadDTO the clientLeadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientLeadDTO, or with status 400 (Bad Request) if the clientLead has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-leads")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<ClientLeadDTO> createClientLead(@RequestBody ClientLead_Simple_DTO clientLeadDTO) throws URISyntaxException {
        log.debug("REST request to save ClientLead : {}", clientLeadDTO);
        if (clientLeadDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientLead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientLeadDTO result = clientLeadService.save(clientLeadDTO);
        return ResponseEntity.created(new URI("/api/client-leads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-leads : Updates an existing clientLead.
     *
     * @param clientLeadDTO the clientLeadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientLeadDTO,
     * or with status 400 (Bad Request) if the clientLeadDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientLeadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-leads")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<ClientLeadDTO> updateClientLead(@RequestBody ClientLead_Simple_DTO clientLeadDTO) throws URISyntaxException {
        log.debug("REST request to update ClientLead : {}", clientLeadDTO);
        if (clientLeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientLeadDTO result = clientLeadService.save(clientLeadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientLeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-leads : get all the clientLeads.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of clientLeads in body
     */
    @GetMapping("/client-leads")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<ClientLeadDTO>> getAllClientLeads(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ClientLeads");
        Page<ClientLeadDTO> page;
        if (eagerload) {
            page = clientLeadService.findAllWithEagerRelationships(pageable);
        } else {
            page = clientLeadService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/client-leads?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-leads/:id : get the "id" clientLead.
     *
     * @param id the id of the clientLeadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientLeadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-leads/{id}")
    @Timed
    public ResponseEntity<ClientLeadDTO> getClientLead(@PathVariable Long id) {
        log.debug("REST request to get ClientLead : {}", id);
        Optional<ClientLeadDTO> clientLeadDTO = clientLeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientLeadDTO);
    }
    
    /**
    * GET  /client-leads/ext/:id : get the clientLead by "ExtId" .
    *
    * @param id the 	ExtId of the clientLeadDTO to retrieve
    * @return the ResponseEntity with status 200 (OK) and with body the clientLeadDTO, or with status 404 (Not Found)
    */
	   @GetMapping("/client-leads/ext/{id}")
	   @Timed
	   public ResponseEntity<ClientLeadDTO> getClientLeadByExtId(@PathVariable Long id) {
	       log.debug("REST request to get ClientLead by extId: {}", id);
	       Optional<ClientLeadDTO> clientLeadDTO = clientLeadService.findOneByExtId(id);
	       return ResponseUtil.wrapOrNotFound(clientLeadDTO);
	   }

    
    /**
     * DELETE  /client-leads/:id : delete the "id" clientLead.
     *
     * @param id the id of the clientLeadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-leads/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<Void> deleteClientLead(@PathVariable Long id) {
        log.debug("REST request to delete ClientLead : {}", id);
        clientLeadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * PUT  /client-leads/add/:eventId/:userExtId : add an the "userExtId" user as an lead for the "eventId" event 
     *
     * @param eventId the eventId to update
     * @param userExtId the userExtId to add
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgAdminDTO,
     * or with status 400 (Bad Request) if the one of the orgId or userExtId is not valid,
     * or with status 500 (Internal Server Error) if the orgAdmin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-leads/add/{eventId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<ClientLeadDTO> addClientLead(@PathVariable Long eventId, @RequestParam(required = false, value="userExtId") Long userExtId, @RequestParam(required = false, value="email") String email) throws URISyntaxException {
        log.debug("REST request to add userExt {} to Event : {}", userExtId, eventId);
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
                    Sets.newHashSet(AuthoritiesConstants.CLIENT_LEAD, AuthoritiesConstants.USER));
            //create userExt if not exists
            UserExt createIfNotExists = userExtService.createIfNotExists(null, email);
            if(createIfNotExists == null) throw new BadRequestAlertException("Error creating new UserExt", ENTITY_NAME, "userext null");
            userExtId = createIfNotExists.getId();
        }

        ClientLeadDTO clientLeadDTO = clientLeadService.addClientLead(eventId, userExtId);
        
        return ResponseUtil.wrapOrNotFound(Optional.of(clientLeadDTO));
    }
    @DeleteMapping("/client-leads/del/{eventId}/{userExtId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<ClientLeadDTO> delClientLead(@PathVariable Long eventId, @PathVariable Long userExtId) throws URISyntaxException {
        log.debug("REST request to add userExt {} to Event : {}", userExtId, eventId);
        if (eventId == null) {
            throw new BadRequestAlertException("Invalid eventId", ENTITY_NAME, "idnull");
        }
        if (userExtId == null) {
            throw new BadRequestAlertException("Invalid userExtId", ENTITY_NAME, "idnull");
        }

        ClientLeadDTO clientLeadDTO = clientLeadService.delClientLead(eventId, userExtId);
        
        
        return ResponseUtil.wrapOrNotFound(Optional.of(clientLeadDTO));
    }
}
