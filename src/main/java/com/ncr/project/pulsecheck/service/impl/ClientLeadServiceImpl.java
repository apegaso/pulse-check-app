package com.ncr.project.pulsecheck.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.domain.Event;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.repository.EventRepository;
import com.ncr.project.pulsecheck.repository.UserExtRepository;
import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;
import com.ncr.project.pulsecheck.service.mapper.ClientLeadMapper;
import com.ncr.project.pulsecheck.service.mapper.ClientLead_Simple_VMMapper;
import com.ncr.project.pulsecheck.web.rest.errors.BadRequestAlertException;
/**
 * Service Implementation for managing ClientLead.
 */
@Service
@Transactional
public class ClientLeadServiceImpl implements ClientLeadService {

    private final Logger log = LoggerFactory.getLogger(ClientLeadServiceImpl.class);

    private final ClientLeadRepository clientLeadRepository;
    private final UserExtRepository userExtRepository;
    private final EventRepository eventRepository;

    private final ClientLeadMapper clientLeadMapper;
    private final ClientLead_Simple_VMMapper clientLead_Simple_VMMapper;

    public ClientLeadServiceImpl(ClientLeadRepository clientLeadRepository, ClientLeadMapper clientLeadMapper, ClientLead_Simple_VMMapper clientLead_Simple_VMMapper, UserExtRepository userExtRepository, EventRepository eventRepository) {
        this.clientLeadRepository = clientLeadRepository;
        this.clientLeadMapper = clientLeadMapper;
		this.clientLead_Simple_VMMapper = clientLead_Simple_VMMapper;
        this.userExtRepository = userExtRepository;
        this.eventRepository = eventRepository;
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

	@Override
	public ClientLeadDTO save(ClientLead_Simple_DTO clientLeadDTO) {
		log.debug("Request to save ClientLead_Simple : {}", clientLeadDTO);
        ClientLead clientLead = clientLead_Simple_VMMapper.toEntity(clientLeadDTO);
        clientLead = clientLeadRepository.save(clientLead);
        return clientLeadMapper.toDto(clientLead);
	}

	@Override
	public Optional<ClientLeadDTO> findOneByExtId(Long id) {
		log.debug("Request to get ClientLead by ExtId: {}", id);
		Optional<UserExt> findById = userExtRepository.findById(id);
		if(findById.isPresent()) {
			Optional<ClientLead> oret = Optional.of(findById.get().getClientLead());
			return oret.map(clientLeadMapper::toDto);
		}
		return null;
	}

    @Override
    public ClientLeadDTO addClientLead(Long eventId, Long userExtId) {
        Optional<UserExt> userExt = userExtRepository.findById(userExtId);
        if(!userExt.isPresent()){
            throw new BadRequestAlertException("UserExt not exists", "ClientLead", "id not exist");
        }
        ClientLead ret = createIfNotExists(userExt.get());

        Optional<Event> event = eventRepository.findById(eventId);
        if(!event.isPresent()){
            throw new BadRequestAlertException("Event not exists", "ClientLead", "id not exist");
        }
        ret.addEvents(event.get());

        ret = clientLeadRepository.save(ret);
        return clientLeadMapper.toDto(ret);
    }

    @Override
    public ClientLead createIfNotExists(UserExt userExt) {
        ClientLead ret = userExt.getClientLead();
        if(ret != null) return ret;
        ret = new ClientLead();
        ret.setUserExt(userExt);
        ret = clientLeadRepository.save(ret);
        ret = clientLeadRepository.findOneWithEagerRelationships(ret.getId()).get();
        return ret;
    }
}
