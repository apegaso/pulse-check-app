package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.OrgAdminDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrgAdmin and its DTO OrgAdminDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface OrgAdminMapper extends EntityMapper<OrgAdminDTO, OrgAdmin> {



    default OrgAdmin fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrgAdmin orgAdmin = new OrgAdmin();
        orgAdmin.setId(id);
        return orgAdmin;
    }
}
