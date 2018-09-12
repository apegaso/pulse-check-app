package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Organization.
 */
public interface OrganizationService {

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    OrganizationDTO save(OrganizationDTO organizationDTO);

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrganizationDTO> findAll(Pageable pageable);
    /**
     * Get all the organizations and events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<OrganizationAndEventsVM> findAllWithEvents(Pageable pageable);

    /**
     * Get the "id" organization.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" organization.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
