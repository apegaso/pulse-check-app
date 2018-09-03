package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.QuestionCategoryService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.service.dto.QuestionCategoryDTO;
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
 * REST controller for managing QuestionCategory.
 */
@RestController
@RequestMapping("/api")
public class QuestionCategoryResource {

    private final Logger log = LoggerFactory.getLogger(QuestionCategoryResource.class);

    private static final String ENTITY_NAME = "questionCategory";

    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryResource(QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    /**
     * POST  /question-categories : Create a new questionCategory.
     *
     * @param questionCategoryDTO the questionCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionCategoryDTO, or with status 400 (Bad Request) if the questionCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-categories")
    @Timed
    public ResponseEntity<QuestionCategoryDTO> createQuestionCategory(@RequestBody QuestionCategoryDTO questionCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionCategory : {}", questionCategoryDTO);
        if (questionCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionCategoryDTO result = questionCategoryService.save(questionCategoryDTO);
        return ResponseEntity.created(new URI("/api/question-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-categories : Updates an existing questionCategory.
     *
     * @param questionCategoryDTO the questionCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionCategoryDTO,
     * or with status 400 (Bad Request) if the questionCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-categories")
    @Timed
    public ResponseEntity<QuestionCategoryDTO> updateQuestionCategory(@RequestBody QuestionCategoryDTO questionCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionCategory : {}", questionCategoryDTO);
        if (questionCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionCategoryDTO result = questionCategoryService.save(questionCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-categories : get all the questionCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionCategories in body
     */
    @GetMapping("/question-categories")
    @Timed
    public List<QuestionCategoryDTO> getAllQuestionCategories() {
        log.debug("REST request to get all QuestionCategories");
        return questionCategoryService.findAll();
    }

    /**
     * GET  /question-categories/:id : get the "id" questionCategory.
     *
     * @param id the id of the questionCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/question-categories/{id}")
    @Timed
    public ResponseEntity<QuestionCategoryDTO> getQuestionCategory(@PathVariable Long id) {
        log.debug("REST request to get QuestionCategory : {}", id);
        Optional<QuestionCategoryDTO> questionCategoryDTO = questionCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionCategoryDTO);
    }

    /**
     * DELETE  /question-categories/:id : delete the "id" questionCategory.
     *
     * @param id the id of the questionCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionCategory(@PathVariable Long id) {
        log.debug("REST request to delete QuestionCategory : {}", id);
        questionCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
