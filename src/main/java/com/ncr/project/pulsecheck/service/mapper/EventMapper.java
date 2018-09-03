package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventMapper extends EntityMapper<EventDTO, Event> {


    @Mapping(target = "organizations", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "leads", ignore = true)
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
