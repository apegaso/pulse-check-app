package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;

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

    public ClientLeadResource(ClientLeadService clientLeadService) {
        this.clientLeadService = clientLeadService;
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
}
