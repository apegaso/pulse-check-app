package com.ncr.project.pulsecheck.service.mapper;

import java.util.stream.Collectors;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface OrganizationAndEventsVMMapper extends EntityMapper<OrganizationAndEventsVM, Organization> {

    EventMapper eventMapper = Mappers.getMapper(EventMapper.class);
    OrganizationMapper organizationMapper = Mappers.getMapper(OrganizationMapper.class);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "admins", ignore = true)
    Organization toEntity(OrganizationAndEventsVM organizationDTO);

 

    default OrganizationAndEventsVM toDto(Organization organization) {
        OrganizationAndEventsVM ret = new OrganizationAndEventsVM(organizationMapper.toDto(organization));
        ret.setEvents(organization.getEvents().stream().map(eventMapper::toDto).collect(Collectors.toList()));

        return ret;
    }
}
