package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.CategoryLevelService;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
import com.ncr.project.pulsecheck.web.rest.util.HeaderUtil;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDTO;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDetailsVM;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CategoryLevel.
 */
@RestController
@RequestMapping("/api")
public class CategoryLevelResource {

    private final Logger log = LoggerFactory.getLogger(CategoryLevelResource.class);

    private static final String ENTITY_NAME = "categoryLevel";

    private final CategoryLevelService categoryLevelService;


    public CategoryLevelResource(CategoryLevelService categoryLevelService) {
        this.categoryLevelService = categoryLevelService;
    }

    /**
     * POST  /category-levels : Create a new categoryLevel.
     *
     * @param categoryLevelDTO the categoryLevelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryLevelDTO, or with status 400 (Bad Request) if the categoryLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/category-levels")
    @Timed
    public ResponseEntity<CategoryLevelDTO> createCategoryLevel(@Valid @RequestBody CategoryLevelDTO categoryLevelDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryLevel : {}", categoryLevelDTO);
        if (categoryLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryLevelDTO result = categoryLevelService.save(categoryLevelDTO);
        return ResponseEntity.created(new URI("/api/category-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /category-levels : Updates an existing categoryLevel.
     *
     * @param categoryLevelDTO the categoryLevelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryLevelDTO,
     * or with status 400 (Bad Request) if the categoryLevelDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoryLevelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/category-levels")
    @Timed
    public ResponseEntity<CategoryLevelDTO> updateCategoryLevel(@Valid @RequestBody CategoryLevelDTO categoryLevelDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryLevel : {}", categoryLevelDTO);
        if (categoryLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryLevelDTO result = categoryLevelService.save(categoryLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoryLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /category-levels : get all the categoryLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoryLevels in body
     */
    @GetMapping("/category-levels")
    @Timed
    public List<CategoryLevelDTO> getAllCategoryLevels() {
        log.debug("REST request to get all CategoryLevels");
        return categoryLevelService.findAll();
    }

    /**
     * GET  /category-levels/:id : get the "id" categoryLevel.
     *
     * @param id the id of the categoryLevelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryLevelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/category-levels/{id}")
    @Timed
    public ResponseEntity<CategoryLevelDetailsVM> getCategoryLevel(@PathVariable Long id) {
        log.debug("REST request to get CategoryLevel : {}", id);
        Optional<CategoryLevelDetailsVM> categoryLevelDTO = categoryLevelService.findOneWithDetails(id);
        return ResponseUtil.wrapOrNotFound(categoryLevelDTO);
    }

    /**
     * DELETE  /category-levels/:id : delete the "id" categoryLevel.
     *
     * @param id the id of the categoryLevelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/category-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoryLevel(@PathVariable Long id) {
        log.debug("REST request to delete CategoryLevel : {}", id);
        categoryLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
