package com.ncr.project.pulsecheck.service;

import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.service.dto.ParticipantDTO;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing Participant.
 */
public interface ParticipantService {

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save
     * @return the persisted entity
     */
    ParticipantDTO save(ParticipantDTO participantDTO);

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParticipantDTO> findAll(Pageable pageable);

    /**
     * Get all the Participant with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ParticipantDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" participant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" participant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Participant createIfNotExists(UserExt userExt);

	ParticipantDTO addParticipant(Long eventId, Long userExtId);
    void delParticipant(Long eventId, Long userExtId);
	Set<ParticipantDTO> findAllByEventId(Long id);
}
