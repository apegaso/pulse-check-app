package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.OrgAdminService;
import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.domain.Organization;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.OrgAdminRepository;
import com.ncr.project.pulsecheck.service.dto.OrgAdminDTO;
import com.ncr.project.pulsecheck.service.mapper.OrgAdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * Service Implementation for managing OrgAdmin.
 */
@Service
@Transactional
public class OrgAdminServiceImpl implements OrgAdminService {

    private final Logger log = LoggerFactory.getLogger(OrgAdminServiceImpl.class);

    private final OrgAdminRepository orgAdminRepository;

    private final OrgAdminMapper orgAdminMapper;

    public OrgAdminServiceImpl(OrgAdminRepository orgAdminRepository, OrgAdminMapper orgAdminMapper) {
        this.orgAdminRepository = orgAdminRepository;
        this.orgAdminMapper = orgAdminMapper;
    }

    /**
     * Save a orgAdmin.
     *
     * @param orgAdminDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrgAdminDTO save(OrgAdminDTO orgAdminDTO) {
        log.debug("Request to save OrgAdmin : {}", orgAdminDTO);
        OrgAdmin orgAdmin = orgAdminMapper.toEntity(orgAdminDTO);
        orgAdmin = orgAdminRepository.save(orgAdmin);
        return orgAdminMapper.toDto(orgAdmin);
    }

    /**
     * Get all the orgAdmins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrgAdminDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgAdmins");
        return orgAdminRepository.findAll(pageable)
            .map(orgAdminMapper::toDto);
    }

    /**
     * Get all the OrgAdmin with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<OrgAdminDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orgAdminRepository.findAllWithEagerRelationships(pageable).map(orgAdminMapper::toDto);
    }
    

    /**
     * Get one orgAdmin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrgAdminDTO> findOne(Long id) {
        log.debug("Request to get OrgAdmin : {}", id);
        return orgAdminRepository.findOneWithEagerRelationships(id)
            .map(orgAdminMapper::toDto);
    }

    /**
     * Delete the orgAdmin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrgAdmin : {}", id);
        orgAdminRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrgAdminDTO> findOneByUserExtEmail(String email) {
        log.debug("Request to get OrgAdmin by UserExt email : {}", email);
        return orgAdminRepository.findOneByUserExtEmail(email).map(orgAdminMapper::toDto);
    }

    @Override
    public void removeOrganization(Organization org) {
        org.getAdmins().forEach(admin -> {
            admin.removeOrganizations(org);
        });
    }

    @Override
    public OrgAdmin createIfNotExists(UserExt existingUserExt) {
        OrgAdmin orgAdmin = existingUserExt.getOrgAdmin();
        if(orgAdmin != null) return orgAdmin;

        orgAdmin = new OrgAdmin();
        orgAdmin.setUserExt(existingUserExt);
        orgAdmin = orgAdminRepository.save(orgAdmin);
        orgAdmin = orgAdminRepository.findOneWithEagerRelationships(orgAdmin.getId()).get();
        return orgAdmin;
	}
}
