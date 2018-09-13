package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndAdminVM;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;
import com.ncr.project.pulsecheck.web.rest.vm.UserEmailVM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

	OrganizationAndAdminVM setAdmins(OrganizationDTO org, List<UserEmailVM> admins);

	Optional<OrganizationAndAdminVM> findOneWithAdmins(Long id);

	Optional<OrganizationAndEventsVM> findOneWithEvents(Long id);

	
}
