package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClientLead and its DTO ClientLeadDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface ClientLeadMapper extends EntityMapper<ClientLeadDTO, ClientLead> {



    default ClientLead fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientLead clientLead = new ClientLead();
        clientLead.setId(id);
        return clientLead;
    }
}
