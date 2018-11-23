package com.ncr.project.pulsecheck.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.OrganizationService;
import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndAdminVM;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    private static final String ENTITY_NAME = "organization";

    private final OrganizationService organizationService;

    

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * POST  /organizations : Create a new organization.
     *
     * @param organizationDTO the organizationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationDTO, or with status 400 (Bad Request) if the organization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizations")
    @Timed
    public ResponseEntity<OrganizationAndAdminVM> createOrganization(@Valid @RequestBody OrganizationAndAdminVM organizationDTO) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organizationDTO);
        if (organizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationDTO org = organizationService.save(organizationDTO);
        OrganizationAndAdminVM result = organizationService.setAdmins(org,organizationDTO.getAdmins());
        log.debug("REST request to save Organization : {}", organizationDTO);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organization.
     *
     * @param organizationDTO the organizationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationDTO,
     * or with status 400 (Bad Request) if the organizationDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizations")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.NCR_ADMIN})
    public ResponseEntity<OrganizationAndAdminVM> updateOrganization(@Valid @RequestBody OrganizationAndAdminVM organizationDTO) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organizationDTO);
        if (organizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationDTO org = organizationService.save(organizationDTO);
        OrganizationAndAdminVM result = organizationService.setAdmins(org,organizationDTO.getAdmins());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations")
    @Timed
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of Organizations");
        Page<OrganizationDTO> page = organizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/organizations/events")
    @Timed
    public ResponseEntity<List<OrganizationAndEventsVM>> getAllOrganizationsAndEvents(Pageable pageable) {
        log.debug("REST request to get a page of Organizations");
        Page<OrganizationAndEventsVM> page = organizationService.findAllWithEvents(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations/events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/organizations/events/{id}")
    @Timed
    public ResponseEntity<OrganizationAndEventsVM> getOrganizationAndEvents(@PathVariable Long id) {
        log.debug("REST request to get aa organization ad related events");
        Optional<OrganizationAndEventsVM> organizationDTO = organizationService.findOneWithEvents(id);
        return ResponseUtil.wrapOrNotFound(organizationDTO);
    }
    
    @GetMapping("/organizations/admins/{id}")
    @Timed
    public ResponseEntity<OrganizationAndAdminVM> getOrganizationAdmin(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Optional<OrganizationAndAdminVM> organizationDTO = organizationService.findOneWithAdmins(id);
        return ResponseUtil.wrapOrNotFound(organizationDTO);
    }
    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param id the id of the organizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Optional<OrganizationDTO> organizationDTO = organizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationDTO);
    }


    
    /**
     * DELETE  /organizations/:id : delete the "id" organization.
     *
     * @param id the id of the organizationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
