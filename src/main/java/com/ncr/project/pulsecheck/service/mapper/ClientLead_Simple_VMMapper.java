package com.ncr.project.pulsecheck.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.domain.Event;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.repository.EventRepository;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;

@Service
public class ClientLead_Simple_VMMapper {

//    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final ClientLeadMapper clientLeadMapper;
    private final ClientLeadRepository clientLeadRepository;

    public ClientLead_Simple_VMMapper(
//    		EventMapper eventMapper, 
    		ClientLeadMapper clientLeadMapper, ClientLeadRepository clientLeadRepository, EventRepository eventRepository){
//        this.eventMapper = eventMapper;
        this.clientLeadMapper = clientLeadMapper;
        this.clientLeadRepository = clientLeadRepository;
        this.eventRepository = eventRepository;
        
    }

    public ClientLead toEntity(ClientLead_Simple_DTO clientLeadDTO) {
    	Optional<ClientLead> findById = clientLeadRepository.findById(clientLeadDTO.getId());
    	ClientLead clientLead = findById.orElse(clientLeadMapper.fromId(clientLeadDTO.getId()));
    	
    	Set<Long> ids = new HashSet<>();
    	clientLeadDTO.getEvents().forEach(e -> ids.add(e.getId()));
		List<Event> findAllById = eventRepository.findAllById(ids);
		Set<Event> events = new HashSet<>();
		events.addAll(findAllById);
		clientLead.setEvents(events);
    	
    	return clientLead;
    }

}
