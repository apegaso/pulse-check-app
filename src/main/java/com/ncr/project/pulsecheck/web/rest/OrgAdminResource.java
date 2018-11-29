package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.domain.Organization;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.OrgAdminRepository;
import com.ncr.project.pulsecheck.repository.OrganizationRepository;
import com.ncr.project.pulsecheck.repository.UserExtRepository;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.OrgAdminService;
import com.ncr.project.pulsecheck.service.OrganizationService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.OrgAdminDTO;
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
 * REST controller for managing OrgAdmin.
 */
@RestController
@RequestMapping("/api")
public class OrgAdminResource {

    private final Logger log = LoggerFactory.getLogger(OrgAdminResource.class);

    private static final String ENTITY_NAME = "orgAdmin";

    private final OrgAdminService orgAdminService;
    


    public OrgAdminResource(OrgAdminService orgAdminService) {
        this.orgAdminService = orgAdminService;
    }

    /**
     * POST  /org-admins : Create a new orgAdmin.
     *
     * @param orgAdminDTO the orgAdminDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orgAdminDTO, or with status 400 (Bad Request) if the orgAdmin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/org-admins")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<OrgAdminDTO> createOrgAdmin(@RequestBody OrgAdminDTO orgAdminDTO) throws URISyntaxException {
        log.debug("REST request to save OrgAdmin : {}", orgAdminDTO);
        if (orgAdminDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgAdmin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgAdminDTO result = orgAdminService.save(orgAdminDTO);
        return ResponseEntity.created(new URI("/api/org-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /org-admins : Updates an existing orgAdmin.
     *
     * @param orgAdminDTO the orgAdminDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgAdminDTO,
     * or with status 400 (Bad Request) if the orgAdminDTO is not valid,
     * or with status 500 (Internal Server Error) if the orgAdminDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/org-admins")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<OrgAdminDTO> updateOrgAdmin(@RequestBody OrgAdminDTO orgAdminDTO) throws URISyntaxException {
        log.debug("REST request to update OrgAdmin : {}", orgAdminDTO);
        if (orgAdminDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrgAdminDTO result = orgAdminService.save(orgAdminDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orgAdminDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /org-admins : get all the orgAdmins.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of orgAdmins in body
     */
    @GetMapping("/org-admins")
    @Timed
    public ResponseEntity<List<OrgAdminDTO>> getAllOrgAdmins(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of OrgAdmins");
        Page<OrgAdminDTO> page;
        if (eagerload) {
            page = orgAdminService.findAllWithEagerRelationships(pageable);
        } else {
            page = orgAdminService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/org-admins?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /org-admins/:id : get the "id" orgAdmin.
     *
     * @param id the id of the orgAdminDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orgAdminDTO, or with status 404 (Not Found)
     */
    @GetMapping("/org-admins/{id}")
    @Timed
    public ResponseEntity<OrgAdminDTO> getOrgAdmin(@PathVariable Long id) {
        log.debug("REST request to get OrgAdmin : {}", id);
        Optional<OrgAdminDTO> orgAdminDTO = orgAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgAdminDTO);
    }

    /**
     * DELETE  /org-admins/:id : delete the "id" orgAdmin.
     *
     * @param id the id of the orgAdminDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/org-admins/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteOrgAdmin(@PathVariable Long id) {
        log.debug("REST request to delete OrgAdmin : {}", id);
        orgAdminService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * PUT  /org-admins/add/:orgId/:userExtId : add an the "userExtId" user as an admin for the "orgId" organization 
     *
     * @param orgId the orgId to update
     * @param userExtId the userExtId to add
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgAdminDTO,
     * or with status 400 (Bad Request) if the one of the orgId or userExtId is not valid,
     * or with status 500 (Internal Server Error) if the orgAdmin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/org-admins/add/{orgId}/{userExtId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<OrgAdminDTO> addOrgAdmin(@PathVariable Long orgId, @PathVariable Long userExtId) throws URISyntaxException {
        log.debug("REST request to add userExt {} to Org : {}", userExtId, orgId);
        if (orgId == null) {
            throw new BadRequestAlertException("Invalid orgId", ENTITY_NAME, "idnull");
        }
        if (userExtId == null) {
            throw new BadRequestAlertException("Invalid userExtId", ENTITY_NAME, "idnull");
        }

        OrgAdminDTO orgAdminDTO = orgAdminService.addOrgAdmin(orgId, userExtId);
        
        return ResponseUtil.wrapOrNotFound(Optional.of(orgAdminDTO));
    }


    @DeleteMapping("/org-admins/del/{orgId}/{userExtId}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<OrgAdminDTO> delOrgAdmin(@PathVariable Long orgId, @PathVariable Long userExtId) throws URISyntaxException {
        log.debug("REST request to del userExt {} to Org : {}", userExtId, orgId);
        if (orgId == null) {
            throw new BadRequestAlertException("Invalid orgId", ENTITY_NAME, "idnull");
        }
        if (userExtId == null) {
            throw new BadRequestAlertException("Invalid userExtId", ENTITY_NAME, "idnull");
        }

        OrgAdminDTO orgAdminDTO = orgAdminService.delOrgAdmin(orgId, userExtId);
        
        return ResponseUtil.wrapOrNotFound(Optional.of(orgAdminDTO));
    }
}
