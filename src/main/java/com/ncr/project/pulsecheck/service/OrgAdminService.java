package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.domain.Organization;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.service.dto.OrgAdminDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OrgAdmin.
 */
public interface OrgAdminService {

    /**
     * Save a orgAdmin.
     *
     * @param orgAdminDTO the entity to save
     * @return the persisted entity
     */
    OrgAdminDTO save(OrgAdminDTO orgAdminDTO);

    /**
     * Get all the orgAdmins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrgAdminDTO> findAll(Pageable pageable);

    /**
     * Get all the OrgAdmin with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<OrgAdminDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" orgAdmin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrgAdminDTO> findOne(Long id);

    /**
     * Delete the "id" orgAdmin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the OrgAdmin by the UserExt email
     *
     * @param email the email of the UserExt entity
     */
	Optional<OrgAdminDTO> findOneByUserExtEmail(String email);

    /**
     * Delete the mappings with Organizations
     *
     * @param id the id of the organization
     */
	void removeOrganization(Organization org);

	OrgAdmin createIfNotExists(UserExt existingUserExt);

	OrgAdminDTO addOrgAdmin(Long orgId, Long userExtId);

	OrgAdminDTO delOrgAdmin(Long orgId, Long userExtId);
}
