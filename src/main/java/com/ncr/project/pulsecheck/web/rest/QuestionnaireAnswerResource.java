package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.QuestionnaireAnswerService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.web.rest.util.PaginationUtil;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;
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
 * REST controller for managing QuestionnaireAnswer.
 */
@RestController
@RequestMapping("/api")
public class QuestionnaireAnswerResource {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireAnswerResource.class);

    private static final String ENTITY_NAME = "questionnaireAnswer";

    private final QuestionnaireAnswerService questionnaireAnswerService;

    public QuestionnaireAnswerResource(QuestionnaireAnswerService questionnaireAnswerService) {
        this.questionnaireAnswerService = questionnaireAnswerService;
    }

    /**
     * POST  /questionnaire-answers : Create a new questionnaireAnswer.
     *
     * @param questionnaireAnswerDTO the questionnaireAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionnaireAnswerDTO, or with status 400 (Bad Request) if the questionnaireAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questionnaire-answers")
    @Timed
    public ResponseEntity<QuestionnaireAnswerDTO> createQuestionnaireAnswer(@Valid @RequestBody QuestionnaireAnswerDTO questionnaireAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionnaireAnswer : {}", questionnaireAnswerDTO);
        if (questionnaireAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionnaireAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionnaireAnswerDTO result = questionnaireAnswerService.save(questionnaireAnswerDTO);
        return ResponseEntity.created(new URI("/api/questionnaire-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questionnaire-answers : Updates an existing questionnaireAnswer.
     *
     * @param questionnaireAnswerDTO the questionnaireAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionnaireAnswerDTO,
     * or with status 400 (Bad Request) if the questionnaireAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionnaireAnswerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questionnaire-answers")
    @Timed
    public ResponseEntity<QuestionnaireAnswerDTO> updateQuestionnaireAnswer(@Valid @RequestBody QuestionnaireAnswerDTO questionnaireAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionnaireAnswer : {}", questionnaireAnswerDTO);
        if (questionnaireAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionnaireAnswerDTO result = questionnaireAnswerService.save(questionnaireAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionnaireAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questionnaire-answers : get all the questionnaireAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionnaireAnswers in body
     */
    @GetMapping("/questionnaire-answers")
    @Timed
    public ResponseEntity<List<QuestionnaireAnswerDTO>> getAllQuestionnaireAnswers(Pageable pageable) {
        log.debug("REST request to get a page of QuestionnaireAnswers");
        Page<QuestionnaireAnswerDTO> page = questionnaireAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questionnaire-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /questionnaire-answers/:id : get the "id" questionnaireAnswer.
     *
     * @param id the id of the questionnaireAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionnaireAnswerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questionnaire-answers/{id}")
    @Timed
    public ResponseEntity<QuestionnaireAnswerDTO> getQuestionnaireAnswer(@PathVariable Long id) {
        log.debug("REST request to get QuestionnaireAnswer : {}", id);
        Optional<QuestionnaireAnswerDTO> questionnaireAnswerDTO = questionnaireAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionnaireAnswerDTO);
    }

    /**
     * DELETE  /questionnaire-answers/:id : delete the "id" questionnaireAnswer.
     *
     * @param id the id of the questionnaireAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questionnaire-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionnaireAnswer(@PathVariable Long id) {
        log.debug("REST request to delete QuestionnaireAnswer : {}", id);
        questionnaireAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
