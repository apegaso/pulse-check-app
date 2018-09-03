package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.mapper.ClientLeadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ClientLead.
 */
@Service
@Transactional
public class ClientLeadServiceImpl implements ClientLeadService {

    private final Logger log = LoggerFactory.getLogger(ClientLeadServiceImpl.class);

    private final ClientLeadRepository clientLeadRepository;

    private final ClientLeadMapper clientLeadMapper;

    public ClientLeadServiceImpl(ClientLeadRepository clientLeadRepository, ClientLeadMapper clientLeadMapper) {
        this.clientLeadRepository = clientLeadRepository;
        this.clientLeadMapper = clientLeadMapper;
    }

    /**
     * Save a clientLead.
     *
     * @param clientLeadDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientLeadDTO save(ClientLeadDTO clientLeadDTO) {
        log.debug("Request to save ClientLead : {}", clientLeadDTO);
        ClientLead clientLead = clientLeadMapper.toEntity(clientLeadDTO);
        clientLead = clientLeadRepository.save(clientLead);
        return clientLeadMapper.toDto(clientLead);
    }

    /**
     * Get all the clientLeads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientLeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientLeads");
        return clientLeadRepository.findAll(pageable)
            .map(clientLeadMapper::toDto);
    }

    /**
     * Get all the ClientLead with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ClientLeadDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clientLeadRepository.findAllWithEagerRelationships(pageable).map(clientLeadMapper::toDto);
    }
    

    /**
     * Get one clientLead by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientLeadDTO> findOne(Long id) {
        log.debug("Request to get ClientLead : {}", id);
        return clientLeadRepository.findOneWithEagerRelationships(id)
            .map(clientLeadMapper::toDto);
    }

    /**
     * Delete the clientLead by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientLead : {}", id);
        clientLeadRepository.deleteById(id);
    }
}
