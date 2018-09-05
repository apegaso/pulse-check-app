package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.service.dto.UserExtDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserExt.
 */
public interface UserExtService {

    /**
     * Save a userExt.
     *
     * @param userExtDTO the entity to save
     * @return the persisted entity
     */
    UserExtDTO save(UserExtDTO userExtDTO);

    /**
     * Get all the userExts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserExtDTO> findAll(Pageable pageable);
    /**
     * Get all the UserExtDTO where ClientLead is null.
     *
     * @return the list of entities
     */
    List<UserExtDTO> findAllWhereClientLeadIsNull();
    /**
     * Get all the UserExtDTO where OrgAdmin is null.
     *
     * @return the list of entities
     */
    List<UserExtDTO> findAllWhereOrgAdminIsNull();
    /**
     * Get all the UserExtDTO where Participant is null.
     *
     * @return the list of entities
     */
    List<UserExtDTO> findAllWhereParticipantIsNull();


    /**
     * Get the "id" userExt.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserExtDTO> findOne(Long id);

    /**
     * Delete the "id" userExt.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
