package com.ncr.project.pulsecheck.service.mapper;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.service.dto.ClientLead_Simple_DTO;

@Service
public class ClientLead_Simple_VMMapper {

    private final EventMapper eventMapper;
    private final ClientLeadMapper clientLeadMapper;
    private final ClientLeadRepository clientLeadRepository;

    public ClientLead_Simple_VMMapper(EventMapper eventMapper, ClientLeadMapper clientLeadMapper, ClientLeadRepository clientLeadRepository){
        this.eventMapper = eventMapper;
        this.clientLeadMapper = clientLeadMapper;
        this.clientLeadRepository = clientLeadRepository;
        
    }

    public ClientLead toEntity(ClientLead_Simple_DTO clientLeadDTO) {
    	Optional<ClientLead> findById = clientLeadRepository.findById(clientLeadDTO.getId());
    	ClientLead clientLead = findById.orElse(clientLeadMapper.fromId(clientLeadDTO.getId()));
//    	clientLead.addEvents(event)
    	
    	return null;
    }

}
