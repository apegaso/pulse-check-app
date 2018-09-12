package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {

    EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "admins", ignore = true)
    Organization toEntity(OrganizationDTO organizationDTO);

    default Organization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }

}
