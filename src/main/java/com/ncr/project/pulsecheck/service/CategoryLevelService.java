package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.CategoryLevelDTO;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDetailsVM;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CategoryLevel.
 */
public interface CategoryLevelService {

    /**
     * Save a categoryLevel.
     *
     * @param categoryLevelDTO the entity to save
     * @return the persisted entity
     */
    CategoryLevelDTO save(CategoryLevelDTO categoryLevelDTO);

    /**
     * Get all the categoryLevels.
     *
     * @return the list of entities
     */
    List<CategoryLevelDTO> findAll();


    /**
     * Get the "id" categoryLevel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CategoryLevelDTO> findOne(Long id);

    /**
     * Delete the "id" categoryLevel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Optional<CategoryLevelDetailsVM> findOneWithDetails(Long id);
}
