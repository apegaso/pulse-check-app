package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.QuestionnaireService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Questionnaire.
 */
@RestController
@RequestMapping("/api")
public class QuestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireResource.class);

    private static final String ENTITY_NAME = "questionnaire";

    private final QuestionnaireService questionnaireService;

    public QuestionnaireResource(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    /**
     * POST  /questionnaires : Create a new questionnaire.
     *
     * @param questionnaireDTO the questionnaireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionnaireDTO, or with status 400 (Bad Request) if the questionnaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questionnaires")
    @Timed
    public ResponseEntity<QuestionnaireDTO> createQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) throws URISyntaxException {
        log.debug("REST request to save Questionnaire : {}", questionnaireDTO);
        if (questionnaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionnaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionnaireDTO result = questionnaireService.save(questionnaireDTO);
        return ResponseEntity.created(new URI("/api/questionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questionnaires : Updates an existing questionnaire.
     *
     * @param questionnaireDTO the questionnaireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionnaireDTO,
     * or with status 400 (Bad Request) if the questionnaireDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionnaireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questionnaires")
    @Timed
    public ResponseEntity<QuestionnaireDTO> updateQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) throws URISyntaxException {
        log.debug("REST request to update Questionnaire : {}", questionnaireDTO);
        if (questionnaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionnaireDTO result = questionnaireService.save(questionnaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionnaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questionnaires : get all the questionnaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionnaires in body
     */
    @GetMapping("/questionnaires")
    @Timed
    public List<QuestionnaireDTO> getAllQuestionnaires() {
        log.debug("REST request to get all Questionnaires");
        return questionnaireService.findAll();
    }

    /**
     * GET  /questionnaires/:id : get the "id" questionnaire.
     *
     * @param id the id of the questionnaireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionnaireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questionnaires/{id}")
    @Timed
    public ResponseEntity<QuestionnaireDTO> getQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to get Questionnaire : {}", id);
        Optional<QuestionnaireDTO> questionnaireDTO = questionnaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionnaireDTO);
    }

    /**
     * DELETE  /questionnaires/:id : delete the "id" questionnaire.
     *
     * @param id the id of the questionnaireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questionnaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to delete Questionnaire : {}", id);
        questionnaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
