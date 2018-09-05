package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.UserExtRepository;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;
import com.ncr.project.pulsecheck.service.mapper.UserExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserExtServiceImpl(UserExtRepository userExtRepository, UserExtMapper userExtMapper) {
        this.userExtRepository = userExtRepository;
        this.userExtMapper = userExtMapper;
    }

    /**
     * Save a userExt.
     *
     * @param userExtDTO the entity to save
     * @return the persisted entity
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
     * Delete the userExt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExt : {}", id);
        userExtRepository.deleteById(id);
    }
}
