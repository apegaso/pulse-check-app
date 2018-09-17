package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.OrgAdminService;
import com.ncr.project.pulsecheck.service.OrganizationService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.domain.Organization;
import com.ncr.project.pulsecheck.domain.User;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.OrganizationRepository;
import com.ncr.project.pulsecheck.security.AuthoritiesConstants;
import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;
import com.ncr.project.pulsecheck.service.mapper.OrganizationAndAdminsVMMapper;
import com.ncr.project.pulsecheck.service.mapper.OrganizationAndEventsVMMapper;
import com.ncr.project.pulsecheck.service.mapper.OrganizationMapper;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndAdminVM;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;
import com.ncr.project.pulsecheck.web.rest.vm.UserEmailVM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;
    
    private final OrganizationAndEventsVMMapper organizationAndEventsVMMapper;
    private final OrganizationAndAdminsVMMapper organizationAndAdminsVMMapper;

    private final UserService userService;
    private final UserExtService userExtService;
    
    private final OrgAdminService orgAdminService;
    
    public OrganizationServiceImpl(OrganizationRepository organizationRepository
    , OrganizationMapper organizationMapper,
    OrganizationAndEventsVMMapper organizationAndEventsVMMapper, 
    UserService userService, 
    UserExtService userExtService, 
    OrganizationAndAdminsVMMapper organizationAndAdminsVMMapper,
    OrgAdminService orgAdminService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.organizationAndEventsVMMapper = organizationAndEventsVMMapper;
        this.userService = userService;
        this.userExtService = userExtService;
        this.orgAdminService = orgAdminService;
        this.organizationAndAdminsVMMapper = organizationAndAdminsVMMapper;
    }

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        OrganizationDTO dto = organizationMapper.toDto(organization);
        List<User> admins = userService.getUsersByAuthorities(AuthoritiesConstants.ADMIN.toString());
        admins.forEach(u -> {
            orgAdminService.findOneByUserExtEmail(u.getEmail()).ifPresent(a -> {
                a.getOrganizations().add(dto);
                orgAdminService.save(a);
            });
        });
        return dto;
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable)
            .map(organizationMapper::toDto);
    }
    /**
     * Get all the organizations and events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationAndEventsVM> findAllWithEvents(Pageable pageable) {
        log.debug("Request to get all Organizationswith events");
        return organizationRepository.findAll(pageable)
            .map(organizationAndEventsVMMapper::toDto);
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationDTO> findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id)
            .map(organizationMapper::toDto);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.findById(id).ifPresent(org -> orgAdminService.removeOrganization(org));
        organizationRepository.deleteById(id);
    }

    @Override
	public OrganizationAndAdminVM setAdmins(OrganizationDTO organizationDTO, List<UserEmailVM> admins){
        if(admins != null && !admins.isEmpty()){
            Organization organization = organizationRepository.findById(organizationDTO.getId()).get();
            admins.forEach(e -> {
                UserExt existingUserExt = userExtService.createIfNotExists(e.getUserExtId(), e.getEmail());
                OrgAdmin orgAdmin = orgAdminService.createIfNotExists(existingUserExt);
                orgAdmin.addOrganizations(organization);   
            });
        }
        List<UserEmailVM> adminsRet = new ArrayList<>();
        Organization organization = organizationRepository.findById(organizationDTO.getId()).get();
        organization.getAdmins().forEach(a -> {
            UserEmailVM tmp = new UserEmailVM(a.getUserExt().getId(),a.getUserExt().getEmail());
            adminsRet.add(tmp);
        });
        OrganizationAndAdminVM ret = new OrganizationAndAdminVM(organizationDTO);
        
		ret.setAdmins(adminsRet);
		return ret;
    }
    
    @Override
    @Transactional(readOnly = true)
	public Optional<OrganizationAndAdminVM> findOneWithAdmins(Long id){
        return organizationRepository.findById(id).map(organizationAndAdminsVMMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationAndEventsVM> findOneWithEvents(Long id) {
        return organizationRepository.findById(id).map(organizationAndEventsVMMapper::toDto);
    }
}
