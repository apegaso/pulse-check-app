package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing UserExt.
 */
@RestController
@RequestMapping("/api")
public class UserExtResource {

    private final Logger log = LoggerFactory.getLogger(UserExtResource.class);

    private static final String ENTITY_NAME = "userExt";

    private final UserExtService userExtService;

    public UserExtResource(UserExtService userExtService) {
        this.userExtService = userExtService;
    }

    /**
     * POST  /user-exts : Create a new userExt.
     *
     * @param userExtDTO the userExtDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtDTO, or with status 400 (Bad Request) if the userExt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-exts")
    @Timed
    public ResponseEntity<UserExtDTO> createUserExt(@Valid @RequestBody UserExtDTO userExtDTO) throws URISyntaxException {
        log.debug("REST request to save UserExt : {}", userExtDTO);
        if (userExtDTO.getId() != null) {
            throw new BadRequestAlertException("A new userExt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtDTO result = userExtService.save(userExtDTO);
        return ResponseEntity.created(new URI("/api/user-exts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-exts : Updates an existing userExt.
     *
     * @param userExtDTO the userExtDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtDTO,
     * or with status 400 (Bad Request) if the userExtDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExtDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-exts")
    @Timed
    public ResponseEntity<UserExtDTO> updateUserExt(@Valid @RequestBody UserExtDTO userExtDTO) throws URISyntaxException {
        log.debug("REST request to update UserExt : {}", userExtDTO);
        if (userExtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExtDTO result = userExtService.save(userExtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExtDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-exts : get all the userExts.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of userExts in body
     */
    @GetMapping("/user-exts")
    @Timed
    public ResponseEntity<List<UserExtDTO>> getAllUserExts(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("clientlead-is-null".equals(filter)) {
            log.debug("REST request to get all UserExts where clientLead is null");
            return new ResponseEntity<>(userExtService.findAllWhereClientLeadIsNull(),
                    HttpStatus.OK);
        }
        if ("orgadmin-is-null".equals(filter)) {
            log.debug("REST request to get all UserExts where orgAdmin is null");
            return new ResponseEntity<>(userExtService.findAllWhereOrgAdminIsNull(),
                    HttpStatus.OK);
        }
        if ("participant-is-null".equals(filter)) {
            log.debug("REST request to get all UserExts where participant is null");
            return new ResponseEntity<>(userExtService.findAllWhereParticipantIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserExts");
        Page<UserExtDTO> page = userExtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-exts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-exts/:id : get the "id" userExt.
     *
     * @param id the id of the userExtDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-exts/{id}")
    @Timed
    public ResponseEntity<UserExtWRelationsDTO> getUserExt(@PathVariable Long id) {
        log.debug("REST request to get UserExt : {}", id);
        Optional<UserExtWRelationsDTO> userExtDTO = userExtService.findOneWithRelationship(id);
        return ResponseUtil.wrapOrNotFound(userExtDTO);
    }

    /**
     * DELETE  /user-exts/:id : delete the "id" userExt.
     *
     * @param id the id of the userExtDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-exts/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExt(@PathVariable Long id) {
        log.debug("REST request to delete UserExt : {}", id);
        userExtService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
