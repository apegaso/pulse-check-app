package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClientLead.
 */
public interface ClientLeadService {

    /**
     * Save a clientLead.
     *
     * @param clientLeadDTO the entity to save
     * @return the persisted entity
     */
    ClientLeadDTO save(ClientLeadDTO clientLeadDTO);

    /**
     * Get all the clientLeads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientLeadDTO> findAll(Pageable pageable);

    /**
     * Get all the ClientLead with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ClientLeadDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" clientLead.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClientLeadDTO> findOne(Long id);

    /**
     * Delete the "id" clientLead.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Save a clientLead with an existing event.
     *
     * @param clientLeadDTO the entity to save
     * @return the persisted entity
     */
	ClientLeadDTO save(ClientLead_Simple_DTO clientLeadDTO);
}
