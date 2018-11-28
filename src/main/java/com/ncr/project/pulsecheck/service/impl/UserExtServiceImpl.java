package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.service.OrgAdminService;
import com.ncr.project.pulsecheck.service.ParticipantService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.UserExtRepository;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;
import com.ncr.project.pulsecheck.service.mapper.OrganizationAndEventsVMMapper;
import com.ncr.project.pulsecheck.service.mapper.UserEventsVMMapper;
import com.ncr.project.pulsecheck.service.mapper.UserExtMapper;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;
import com.ncr.project.pulsecheck.web.rest.vm.UserEventsVM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing UserExt.
 */
@Service
@Transactional
public class UserExtServiceImpl implements UserExtService {

    private final Logger log = LoggerFactory.getLogger(UserExtServiceImpl.class);

    private final UserExtRepository userExtRepository;

    private final UserExtMapper userExtMapper;
    
    private final UserEventsVMMapper userEventsVMMapper;
    
    private final OrganizationAndEventsVMMapper organizationAndEventsVMMapper;

    private final ClientLeadService clientLeadService;
    
    private final OrgAdminService orgAdminService;
    
    private final ParticipantService participantService;

    public UserExtServiceImpl(UserExtRepository userExtRepository
    , UserExtMapper userExtMapper
    , UserEventsVMMapper userEventsVMMapper
    , OrganizationAndEventsVMMapper organizationAndEventsVMMapper
    ,ClientLeadService clientLeadService
    ,OrgAdminService orgAdminService
    ,ParticipantService participantService
    ) {
        this.userExtRepository = userExtRepository;
        this.userExtMapper = userExtMapper;
        this.userEventsVMMapper = userEventsVMMapper;
        this.organizationAndEventsVMMapper = organizationAndEventsVMMapper;
        this.clientLeadService = clientLeadService;
        this.orgAdminService=orgAdminService;
        this.participantService = participantService;
    }

    /**
     * Save a userExt.
     *
     * @param userExtDTO the entity to save
     * @retrn the persisted entity
     */
    @Override
    public UserExtDTO save(UserExtDTO userExtDTO) {
        log.debug("Request to save UserExt : {}", userExtDTO);
        UserExt userExt = userExtMapper.toEntity(userExtDTO);
        userExt = userExtRepository.save(userExt);
        return userExtMapper.toDto(userExt);
    }

    /**
     * Get all the userExts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserExtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExts");
        return userExtRepository.findAll(pageable)
            .map(userExtMapper::toDto);
    }



    /**
     *  get all the userExts where ClientLead is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserExtDTO> findAllWhereClientLeadIsNull() {
        log.debug("Request to get all userExts where ClientLead is null");
        return StreamSupport
            .stream(userExtRepository.findAll().spliterator(), false)
            .filter(userExt -> userExt.getClientLead() == null)
            .map(userExtMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the userExts where OrgAdmin is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserExtDTO> findAllWhereOrgAdminIsNull() {
        log.debug("Request to get all userExts where OrgAdmin is null");
        return StreamSupport
            .stream(userExtRepository.findAll().spliterator(), false)
            .filter(userExt -> userExt.getOrgAdmin() == null)
            .map(userExtMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the userExts where Participant is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserExtDTO> findAllWhereParticipantIsNull() {
        log.debug("Request to get all userExts where Participant is null");
        return StreamSupport
            .stream(userExtRepository.findAll().spliterator(), false)
            .filter(userExt -> userExt.getParticipant() == null)
            .map(userExtMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userExt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserExtDTO> findOne(Long id) {
        log.debug("Request to get UserExt : {}", id);
        return userExtRepository.findById(id)
            .map(userExtMapper::toDto);
    }
    /**
     * Get one userExt by email.
     *
     * @param email the email of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserExtDTO> findOneByEmail(String email) {
        log.debug("Request to get UserExt : {}", email);
        return userExtRepository.findByEmail(email)
            .map(userExtMapper::toDto);
    }

    /**
     * Delete the userExt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExt : {}", id);
        userExtRepository.findById(id).ifPresent(userExt -> {
            OrgAdmin orgAdmin = userExt.getOrgAdmin();
            if(orgAdmin != null){
                orgAdminService.delete(orgAdmin.getId());
            }
            ClientLead clientLead = userExt.getClientLead();
            if(clientLead != null){
                clientLeadService.delete(clientLead.getId());
            }
            Participant participant = userExt.getParticipant();
            if (participant != null){
                participantService.delete(participant.getId());
            }
        });
        
        userExtRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEventsVM> findUserEventsVMByEmail(String email) {
        log.debug("Request to findUserEventsVMByEmail : {}", email);
        return userExtRepository.findByEmail(email)
                .map(userEventsVMMapper::userToUserEventsVM);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<OrganizationAndEventsVM>> findUserOrganizationsVMByEmail(String email) {
        log.debug("Request to findUserOrganizationsVMByEmail : {}", email);
        List<OrganizationAndEventsVM> ret = new ArrayList<>();
        userExtRepository.findByEmail(email)
            .map(u -> u.getOrgAdmin())
            .map(oa -> oa.getOrganizations())
            .ifPresent(o -> {
                o.stream()
                .map(organizationAndEventsVMMapper::toDto)
                .forEach(c->ret.add(c));
            });
        
        return Optional.of(ret);
            
        
	}

    @Override
    public UserExt createIfNotExists(Long userExtId, String email) {
        if(userExtId != null && userExtId > 0){
            Optional<UserExt> findById = userExtRepository.findById(userExtId);
            if(findById.isPresent()) return findById.get();
        }
        if(email == null) return null;
        Optional<UserExt> findByEmail = userExtRepository.findByEmail(email);
        if(findByEmail.isPresent()) return findByEmail.get();

        UserExtDTO userExtDTO = new UserExtDTO();
        userExtDTO.setEmail(email);
        UserExt entity = userExtMapper.toEntity(userExtDTO);
        System.out.println("aaaa ********************* before save "+entity);     
        UserExt save = userExtRepository.save(entity);
        System.out.println("aaaa ********************* after save "+save);     
        return save;
    }
}
