package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.UserExtWRelationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExt and its DTO UserExtDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OrganizationMapper.class, ClientLeadMapper.class, OrgAdminMapper.class, ParticipantMapper.class})
public interface UserExtWRelationsMapper extends EntityMapper<UserExtWRelationsDTO, UserExt> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.organizationName", target = "organizationName")
    @Mapping(source = "clientLead", target = "clientLead")
    @Mapping(source = "orgAdmin", target = "orgAdmin")
    @Mapping(source = "participant", target = "participant")
    UserExtWRelationsDTO toDto(UserExt userExt);
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "organizationId", target = "organization")
    @Mapping(target = "clientLead", ignore = true)
    @Mapping(target = "orgAdmin", ignore = true)
    @Mapping(target = "participant", ignore = true)
    UserExt toEntity(UserExtWRelationsDTO userExtDTO);
}
