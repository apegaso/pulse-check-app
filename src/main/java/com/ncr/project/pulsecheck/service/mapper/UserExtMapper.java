package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExt and its DTO UserExtDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OrganizationMapper.class})
public interface UserExtMapper extends EntityMapper<UserExtDTO, UserExt> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "organization.id", target = "organizationId")
    UserExtDTO toDto(UserExt userExt);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "organizationId", target = "organization")
    @Mapping(target = "clientLead", ignore = true)
    @Mapping(target = "orgAdmin", ignore = true)
    @Mapping(target = "participant", ignore = true)
    UserExt toEntity(UserExtDTO userExtDTO);

    default UserExt fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExt userExt = new UserExt();
        userExt.setId(id);
        return userExt;
    }
}
